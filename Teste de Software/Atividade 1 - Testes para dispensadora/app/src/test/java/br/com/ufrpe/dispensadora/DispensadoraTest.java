package br.com.ufrpe.dispensadora;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DispensadoraTest {

    private Dispensadora dispensadora;
    private int[] cedulasIniciais;

    @BeforeEach
    void setUp() {
        cedulasIniciais = new int[]{10, 10, 10, 10, 10, 10};
        dispensadora = new Dispensadora(cedulasIniciais);
    }

    @Test
    void testConstrutorComCedulasNegativas() {
        int[] cedulasNegativas = {-1, 10, 10, 10, 10, 10};
        assertThrows(IllegalArgumentException.class, () -> new Dispensadora(cedulasNegativas),
            "Deveria lançar exceção quando há cédulas negativas");
    }

    @Test
    void testConstrutorComTodasCedulasNegativas() {
        int[] todasNegativas = {-10, -10, -10, -10, -10, -10};
        assertThrows(IllegalArgumentException.class, () -> new Dispensadora(todasNegativas),
            "Deveria lançar exceção quando todas as cédulas são negativas");
    }

    @Test
    void testConstrutorComArrayNulo() {
        assertThrows(IllegalArgumentException.class, () -> new Dispensadora(null),
            "Deveria lançar exceção quando o array é nulo");
    }

    @Test
    void testConstrutorComArrayVazio() {
        int[] arrayVazio = {};
        assertThrows(IllegalArgumentException.class, () -> new Dispensadora(arrayVazio),
            "Deveria lançar exceção quando o array está vazio");
    }

    @Test
    void testConstrutorComArrayMuitoGrande() {
        int[] arrayGrande = new int[10];
        assertThrows(IllegalArgumentException.class, () -> new Dispensadora(arrayGrande),
            "Deveria lançar exceção quando o array tem tamanho incorreto");
    }

    @Test
    void testConstrutorComQuantidadeZeroEmTodasCedulas() {
        int[] todasZeradas = {0, 0, 0, 0, 0, 0};
        assertDoesNotThrow(() -> new Dispensadora(todasZeradas),
            "Máquina sem cédulas deveria ser uma inicialização válida");
    }

    @Test
    void testConstrutorComQuantidadeZeroEmAlgumasCedulas() {
        int[] algumasZeradas = {0, 10, 0, 5, 0, 20};
        assertDoesNotThrow(() -> new Dispensadora(algumasZeradas),
            "Máquina com algumas cédulas zeradas deveria ser válida");
    }

    @Test
    void testConstrutorComValoresMaximosInt() {
        int[] valoresMaximos = {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 
                                Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};
        assertDoesNotThrow(() -> new Dispensadora(valoresMaximos),
            "Valores máximos de int deveriam ser aceitos na inicialização");
    }

    @Test
    void testConstrutorComQuantidadeInvalidaDeCedulas() {
        int[] cedulasInvalidas = {10, 10, 10, 10};
        assertThrows(IllegalArgumentException.class, () -> new Dispensadora(cedulasInvalidas));
    }

    @Test
    void testDispensarValorExatoComUmaCedulaDeCada() throws NaoEhPossivelDispensarValorException {
        int[] resultado = dispensadora.dispensar(187);
        assertArrayEquals(new int[]{1, 1, 1, 1, 1, 1}, resultado);
    }

    @Test
    void testDispensarValorComMultiplasCedulas() throws NaoEhPossivelDispensarValorException {
        int[] resultado = dispensadora.dispensar(280);
        assertArrayEquals(new int[]{2, 1, 1, 1, 0, 0}, resultado);
    }

    @Test
    void testDispensarValorQueNaoPodeSerAtendido() {
        assertThrows(NaoEhPossivelDispensarValorException.class, () -> dispensadora.dispensar(1));
        assertThrows(NaoEhPossivelDispensarValorException.class, () -> dispensadora.dispensar(3));
    }

    @Test
    void testDispensarValorComCedulasEsgotadas() throws NaoEhPossivelDispensarValorException {
        dispensadora.dispensar(1000);
        
        int[] resultado = dispensadora.dispensar(100);
        assertArrayEquals(new int[]{0, 2, 0, 0, 0, 0}, resultado);
    }

    @Test
    void testDispensarValorZerado() throws NaoEhPossivelDispensarValorException {
        int[] resultado = dispensadora.dispensar(0);
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0}, resultado);
    }

    @Test
    void testDispensarValorCombinacaoComplexa() throws NaoEhPossivelDispensarValorException {
        int[] resultado = dispensadora.dispensar(327);
        assertArrayEquals(new int[]{3, 0, 1, 0, 1, 1}, resultado);
    }

    @Test
    void testDispensarValorMaximoPossivel() throws NaoEhPossivelDispensarValorException {
        int[] resultado = dispensadora.dispensar(1870);
        assertArrayEquals(new int[]{10, 10, 10, 10, 10, 10}, resultado);
    }

    @Test
    void testDispensarValorAcimaDoMaximoPossivel() {
        assertThrows(NaoEhPossivelDispensarValorException.class, () -> dispensadora.dispensar(1871));
    }
}
