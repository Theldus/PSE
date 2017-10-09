//Importar as classes
var Utility = Java.type("control.Utility");
var InputOutput = Java.type("control.InputOutput");
var Operation = Java.type("control.Operation");

//Instanciar as classes
var Util = new Utility();
var Io = new InputOutput();
var Op = new Operation();

//Execução dos métodos
var result = Util.getBlank(100, 100);
Io.print(result, "blank.png", "png");