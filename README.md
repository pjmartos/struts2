This plugin leverages a cleaner, uniform URI-to-representation mapping in RESTful Struts2 applications, in combination with the struts2-rest-plugin.

The negotiation plugin honors the <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.1" target="_blank">Accept request header</a> in order to return an appropriate response back to the client; that is, in order to obtain a representation in JSON, XML, etc for a given resource, is no longer necessary (in fact, discouraged) to append a suffix (.json, .xml, etc) to the URI, as promoted by the struts2-rest-plugin. Instead, you are expected to include the Accept request header with a comma-separated list of the content types you'd like the response content to be delivered in. If no server-side handlers exist for none of the given content types, then the server must return a representation of the requested resource in any content type it supports.

<pre>Accept: application/json, application/xml</pre>

Please notice that at this moment the logic for processing the Accept header is still quite primitive. As a matter of fact, this version of the plugin doesn't support wildcards or media parameters; for now, it just assumes the desired data types were given in order of preference.

The classic struts2-rest-plugin includes handlers for the HTML, JSON and XML data types, named <b>html</b>, <b>json</b> and <b>xml</b>, respectively. To configure a handler for one of these content types, you just need to declare a constant in a struts.xml file. The next example shows the minimal set-up necessary to define the handler which will satisfy requests expecting a response in JSON format (application/json):

<pre>&lt;struts>
    &lt;constant name="struts.rest.negotiation.handlerOverride.application/json" value="json" />
&lt;/struts></pre>

 In case you need to override the existing handlers or provide a custom one, you need to declare a corresponding bean, and a constant pointing to it. In the following example we declare a handler for the datatype application/whatever:

<pre>&lt;struts>
    &lt;constant name="struts.rest.negotiation.handlerOverride.application/whatever" value="foo" />
    &lt;bean name="foo" type="org.apache.struts2.rest.handler.ContentTypeHandler"
        class="com.foo.bar.BazHandler" />
&lt;/struts></pre>

The class <b>com.foo.bar.BazHandler</b> in the example is required to implement the interface <b>org.apache.struts2.rest.handler.ContentTypeHandler</b> and return the appropriate value in its <i>getContentType</i> method.

<pre>
    package com.foo.bar;
    
    public class BazHandler implements org.apache.struts2.rest.handler.ContentTypeHandler {
        
        public String getContentType() {
            return "application/whatever";
        }

        public void toObject(Reader in, Object target) throws IOException {
            // Code for unmarshalling from "whatever" would be here
        }
    
        public String fromObject(Object obj, String resultCode, Writer stream) throws IOException {
            // Code for representation logic (formatting to the "whatever" data type) would be here
        }
        
        public String getExtension() {
            // Used for backwards compatibility with struts2-rest-plugin
        }

    }
</pre>

The plugin will ignore any charset information appended to the content type in the String returned via the <i>getContentType</i> method, as well as the case; that is, it will treat "application/whatever", "APPLICATION/WHATEVER", "application/whatever;charset=utf-8" or any other combinations in the same way. As show in the example, the plugin allows the handling of custom hypermedia types, as well as standard, well-known types such as XML, CSV, etc.
