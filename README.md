This plugin allows for a cleaner, uniform URI-to-representation mapping in RESTful Struts2 applications, in combination with the struts2-rest-plugin.

The negotiation plugin honors the Accept request header in order to return an appropriate response back to the client; that is, in order to obtain a representation in JSON, XML, etc for a given resource, is no longer necessary, nor recommended, to append a suffix .json, .xml, etc to the URI, as promoted by the struts2-rest-plugin. Instead, you are expected to include the Accept request header with a comma-separated list of the content types you'd like the response content to be delivered in, in order of preference. If no server-side handlers exist for none of the given content types, then the server must return a representation of the
requested resource in any content type it supports.
