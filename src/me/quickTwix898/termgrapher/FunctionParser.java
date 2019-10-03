package me.quickTwix898.termgrapher;

import groovy.util.Eval;

import java.util.ArrayList;
import java.util.List;

public class FunctionParser {
    private List<String> input;
    private List<String> output;

    public FunctionParser(List<String> preParsed) {
        this.input = preParsed;
        this.output = parse();
    }
    public FunctionParser(String f1, String f2, String f3, String f4, String f5, String f6, String f7, String f8, String f9) {
        this.input = new ArrayList<String>();
        input.add(f1); input.add(f2); input.add(f3); input.add(f4); input.add(f5);
        input.add(f6); input.add(f7); input.add(f8); input.add(f9);
        this.output = parse();
    }

    public List<String> parse() {
        output = new ArrayList<String>();
        for(int i=0; i<input.size(); i++) {
            if(input.get(i).toLowerCase().contains("error")) throw new IllegalStateException("I'm sorry Dave, I'm afraid I can't do that.");
            output.add(input.get(i));
            String implicitMult = "((\\d{1,10})(?=[xM(]))";
            String powerCaret = "((\\(.{1,10}\\))|x|((\\d|\\.){1,5}))\\^(\\(.{1,10}\\)|x|((\\d|\\.){1,5}))";
            String regTrig = "(?<!Math\\.)(sin|cos|tan)";
            String csc = "(?<!Math\\.)csc(\\(.{1,20}\\))"; String sec = "(?<!Math\\.)sec(\\(.{1,20}\\))";
            String cot = "(?<!Math\\.)cot(\\(.{1,20}\\))"; String sqrt = "(?<!Math\\.)sqrt(\\(.{1,20}\\))";
            String ln = "ln(\\(.{1,20}\\))"; String baseLog = "log(\\d{1,5})(\\(.{1,20}\\))";
            String abs = "\\|(.{1,20})\\|"; String singleNum = "(^[^x]{1,}$)";

            output.set(i, output.get(i).replaceAll(implicitMult, "$1*"));
            output.set(i, output.get(i).replaceAll(powerCaret, "Math.pow($1, $5)"));
            output.set(i, output.get(i).replaceAll(regTrig, "Math.$1"));
            output.set(i, output.get(i).replaceAll(csc, "(1.0/Math.sin$1)"));
            output.set(i, output.get(i).replaceAll(sec,"(1.0/Math.cos$1)"));
            output.set(i, output.get(i).replaceAll(cot, "(1.0/Math.tan$1)"));
            output.set(i, output.get(i).replaceAll(sqrt, "Math.sqrt$1"));
            output.set(i, output.get(i).replaceAll(ln, "Math.log$1"));
            output.set(i, output.get(i).replaceAll(baseLog, "Math.log$2/Math.log($1)"));
            output.set(i, output.get(i).replaceAll(abs, "Math.abs($1)"));
            output.set(i, output.get(i).replaceAll(singleNum, "$1+(0*x)"));
        }
        return output;
    }

    public double evalParsed(int position, double mathX) {
        return (Double) Eval.x(mathX, output.get(position));
    }

    public List<String> getOutput() {
        return output;
    }

}
