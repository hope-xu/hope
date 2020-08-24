package com.utils;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Hope
 * Date： 2020/05/22  11:05
 * 描述：
 */
public class ExpressionTools {

    public static boolean praseExpresion(String expText, Map expContText){
        ExpressionParser ep = new SpelExpressionParser();
        EvaluationContext ec = new StandardEvaluationContext();
        for (Object e : expContText.keySet()){
            ec.setVariable((String) e,expContText.get(e));
        }
        return (boolean) ep.parseExpression(expText).getValue(ec);
    }


    public static void main(String[] args) {
        String a = "0";
        Map map = new HashMap();
        map.put("a","0");
        map.put("b","1");

        boolean b = praseExpresion(a, map);


    }


}
