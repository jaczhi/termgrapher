import groovy.util.Eval;

public class FunctionParser {
    private String[] input;
    private String[] output;

    public FunctionParser(String[] preParsed) {
        this.input = preParsed;
        this.output = parse();
    }
    public FunctionParser(String f1, String f2, String f3, String f4, String f5, String f6, String f7, String f8, String f9) {
        this.input = new String[9];
        input[0]=f1; input[1]=f2; input[2]=f3; input[3]=f4; input[4]=f5; input[5]=f6; input[6]=f7; input[7]=f8; input[8]=f9;
        this.output = parse();
    }

    public String[] parse() {
        output = new String[input.length];
        for(int i=0; i<input.length; i++) {
            if(input[i].toLowerCase().contains("error")) throw new IllegalStateException("I'm sorry Dave, I'm afraid I can't do that.");
            output[i] = input[i];
            String implicitMult = "((\\d{1,10})(?=[xM(]))";
            String powerCaret = "((\\(.{1,10}\\))|x|((\\d|\\.){1,5}))\\^(\\(.{1,10}\\)|x|((\\d|\\.){1,5}))";
            String regTrig = "(?<!Math\\.)(sin|cos|tan)";
            String csc = "(?<!Math\\.)csc(\\(.{1,20}\\))"; String sec = "(?<!Math\\.)sec(\\(.{1,20}\\))";
            String cot = "(?<!Math\\.)cot(\\(.{1,20}\\))"; String sqrt = "(?<!Math\\.)sqrt(\\(.{1,20}\\))";
            String ln = "ln(\\(.{1,20}\\))"; String baseLog = "log(\\d{1,5})(\\(.{1,20}\\))";
            String abs = "\\|(.{1,20})\\|";

            output[i] = output[i].replaceAll(implicitMult, "$1*");
            output[i] = output[i].replaceAll(powerCaret, "Math.pow($1, $5)");
            output[i] = output[i].replaceAll(regTrig, "Math.$1");
            output[i] = output[i].replaceAll(csc, "(1.0/Math.sin$1)");
            output[i] = output[i].replaceAll(sec,"(1.0/Math.cos$1)");
            output[i] = output[i].replaceAll(cot, "(1.0/Math.tan$1)");
            output[i] = output[i].replaceAll(sqrt, "Math.sqrt$1");
            output[i] = output[i].replaceAll(ln, "Math.log$1");
            output[i] = output[i].replaceAll(baseLog, "Math.log$2/Math.log($1)");
            output[i] = output[i].replaceAll(baseLog, "Math.abs($1)");
        }
        return output;
    }

    public double evalParsed(int position, double mathX) {
        return (Double) Eval.x(mathX, output[position]);
    }

    public String[] getOutput() {
        return output;
    }

}
