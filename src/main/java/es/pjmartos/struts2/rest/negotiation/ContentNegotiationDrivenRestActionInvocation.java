package es.pjmartos.struts2.rest.negotiation;

import java.util.Map;

import org.apache.struts2.rest.ContentTypeHandlerManager;
import org.apache.struts2.rest.RestActionInvocation;

import com.opensymphony.xwork2.inject.Inject;

public class ContentNegotiationDrivenRestActionInvocation extends
        RestActionInvocation {
    private static final long serialVersionUID = 4265092300575925467L;

    protected ContentNegotiationDrivenRestActionInvocation(
            final Map<String, Object> extraContext, final boolean pushAction) {
        super(extraContext, pushAction);
    }

    @Override
    @Inject("contentNegotiationDriven")
    public final void setMimeTypeHandlerSelector(
            final ContentTypeHandlerManager sel) {
        super.setMimeTypeHandlerSelector(sel);
    }

}
