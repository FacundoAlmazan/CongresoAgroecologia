window.onload = function() {
  //<editor-fold desc="Changeable Configuration Block">

  const swaggerUiIndex = window.location.pathname.indexOf('/swagger-ui');
  const contextPath = swaggerUiIndex >= 0 ? window.location.pathname.substring(0, swaggerUiIndex) : '';

  window.ui = SwaggerUIBundle({
    url: window.location.origin + contextPath + "/openapi",
    dom_id: '#swagger-ui',
    deepLinking: true,
    presets: [
      SwaggerUIBundle.presets.apis,
      SwaggerUIStandalonePreset
    ],
    plugins: [
      SwaggerUIBundle.plugins.DownloadUrl
    ],
    layout: "StandaloneLayout"
  });

  //</editor-fold>
};
