@DEMO
Feature: Prueba de concepto SpringBoot + Cucumber - GOOGLE

  @GOOGLE_SEARCH
  Scenario: caso1-Busqueda en google
    Given que abro la pagina de google
    When escribo la busqueda de: "cantantes de rock"
    Then valido que los resultados sean mayores a 0

#  @GOOGLE_SETTINGS
#  Scenario: caso2-Configuraciones en google
#    Given que abro la pagina de configuracio de google
#    When busco la opcion "Usuario"
#    Then valido que el resultado sea "Autocompletar"