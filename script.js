//Importar as classes
var Utility = Java.type("algorithms.Utility");
var InputOutput = Java.type("inputoutput.InputOutput");
var Operation = Java.type("algorithms.Operation");

//Instanciar as classes
var Util = new Utility();
var Io = new InputOutput();
var Op = new Operation();

//Execução dos métodos
var result = Util.getBlank(100, 100);
Io.print(result, "blank.png", "png");