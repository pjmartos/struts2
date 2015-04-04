This plugin leverages a cleaner, uniform URI-to-representation mapping in RESTful Struts2 applications, in combination with the struts2-rest-plugin.

The negotiation plugin honors the Accept request header in order to return an appropriate response back to the client; that is, in order to obtain a representation in JSON, XML, etc for a given resource, is no longer necessary, nor recommended, to append a suffix .json, .xml, etc to the URI, as promoted by the struts2-rest-plugin. Instead, you are expected to include the Accept request header with a comma-separated list of the content types you'd like the response content to be delivered in, in order of preference. If no server-side handlers exist for none of the given content types, then the server must return a representation of the requested resource in any content type it supports.

To configure a handler for a given content type, you just need to declare a constant in a struts.xml file. The next example shows the minimal set-up necessary to define the handler which will satisfy requests expecting a response in JSON format (application/json):

<struts>
    <constant name="struts.rest.negotiation.handlerOverride.application/json" value="json" />
</struts>

The struts2-rest-plugin includes handlers for the HTML, JSON and XML data types, named html, json and xml, respectively. In case you need to override the existing handlers or provide a custom one, you need to declare a corresponding bean, and a constant pointing to it. In the following example we declare a handler for the datatype application/whatever:

<struts>
    <constant name="struts.rest.negotiation.handlerOverride.application/whatever" value="foo" />
    <bean name="jsonp" type="org.apache.struts2.rest.handler.ContentTypeHandler" class="com.foo.bar.BazHandler" />
</struts>

The class com.foo.bar.BazHandler in the example is required to implement org.apache.struts2.rest.handler.ContentTypeHandler and return the appropriate value in its getContentType method. The plugin will ignore any charset information appended to the content type, as well as the case; that is, it will treat "application/whatever", "APPLICATION/WHATEVER", "application/whatever;charset=utf-8" or any other combinations in the same way. As show in the example, the plugin allows the handling of custom hypermedia types, as well as standard, well-known types such as XML, CSV, etc.
