#include <stdio.h>
#include <jni.h>
#include "CalculadoraJNI.h"

/*
 * M�todo que executa a soma
 */
int soma(int num1, int num2){
	//printf("Acessei o codigo C\n");
    int resultado = num1 + num2;
    return resultado;
}

/*
 * M�todo com a mesma assinatura do CalculadoraJNI.h
 */
JNIEXPORT jint JNICALL Java_my_snippets_jni_CalculadoraJNI_soma(JNIEnv * env, jobject jobj, jint num1, jint num2){
    //chamada ao m�todo da soma
    return soma(num1, num2);
}
