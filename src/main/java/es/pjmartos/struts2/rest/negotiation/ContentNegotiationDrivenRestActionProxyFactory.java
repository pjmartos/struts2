package es.pjmartos.struts2.rest.negotiation;

import java.util.Map;

import org.apache.struts2.rest.RestActionProxyFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;

public class ContentNegotiationDrivenRestActionProxyFactory extends
        RestActionProxyFactory {

    @Override
    public final ActionProxy createActionProxy(final String namespaceP,
            final String actionName, final String methodName,
            final Map<String, Object> extraContext,
            final boolean executeResult, final boolean cleanupContext) {
		final String nSpace = this.namespace;
        if (nSpace == null || namespaceP.startsWith(nSpace)) {
            final ActionInvocation inv =
                    new ContentNegotiationDrivenRestActionInvocation(
                            extraContext, true);
            this.container.inject(inv);
            return this.createActionProxy(inv, namespaceP, actionName,
                    methodName, executeResult, cleanupContext);
        }
        return super.createActionProxy(namespaceP, actionName, methodName,
                extraContext, executeResult, cleanupContext);
    }

}
