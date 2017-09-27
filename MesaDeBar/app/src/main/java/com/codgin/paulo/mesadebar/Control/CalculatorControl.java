package com.codgin.paulo.mesadebar.Control;

/**
 * Created by paulocalado on 12/09/17.
 */

public class CalculatorControl {

    public double dividePorPessoa(double valorProduto, int qtdProduto, int qtdPessoa){
        double resultado =0;

        resultado = (valorProduto*qtdProduto)/qtdPessoa;

        return resultado;
    }

    public double addGorjeta(double total, int gorjeta){
        double resultado = 0;

        resultado = (total*gorjeta)/100;

        return resultado;
    }
}
