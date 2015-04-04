package es.pjmartos.struts2.rest.negotiation;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.rest.DefaultContentTypeHandlerManager;
import org.apache.struts2.rest.handler.ContentTypeHandler;

import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.inject.Inject;

public class ContentNegotiationDrivenContentTypeHandlerManager extends
        DefaultContentTypeHandlerManager {
    private final transient Map<String, ContentTypeHandler> handlers =
            new ConcurrentHashMap<>();

    private static String normalize(final String contentType) {
        final int colonIndex = contentType.indexOf(';');
        final String stripped;
        if (colonIndex < 0) {
            stripped = contentType;
        } else {
            stripped = contentType.substring(0, colonIndex);
        }
        return stripped.toLowerCase(Locale.UK);
    }

    @Override
    public final ContentTypeHandler
            getHandlerForResponse(final HttpServletRequest request,
                    final HttpServletResponse response) {
        final Enumeration<String> acceptHeaders =
                request.getHeaders("Accept"); //$NON-NLS-1$
        while (acceptHeaders.hasMoreElements()) {
            final String accept = acceptHeaders.nextElement();
            final ContentTypeHandler handler = this.handlers.get(accept);
            if (handler != null) {
                return handler;
            }
        }
        return super.getHandlerForResponse(request, response);
    }

    @Override
    @Inject
    public final void setContainer(final Container container) {
        final Set<String> names =
                container.getInstanceNames(ContentTypeHandler.class);
        for (final String name : names) {
            final ContentTypeHandler handler =
                    container.getInstance(ContentTypeHandler.class, name);
            final String contentType =
                    ContentNegotiationDrivenContentTypeHandlerManager
                            .normalize(handler.getContentType());
            if (contentType != null) {
                final String overrideName = container.getInstance(String.class,
                        "struts.rest.negotiation.handlerOverride." //$NON-NLS-1$
                                + contentType);
                if (overrideName == null) {
                    this.handlers.put(contentType, handler);
                } else if (!this.handlers.containsKey(contentType)) {
                    final ContentTypeHandler override =
                            container.getInstance(ContentTypeHandler.class,
                                    overrideName);
                    this.handlers.put(contentType, override);
                }
            }
        }
        super.setContainer(container);
    }

}
