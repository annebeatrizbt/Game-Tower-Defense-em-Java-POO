package principal;

public enum EstadosJogo {

    JOGANDO,
    MENU,
    CONFIGURACOES,
    EDITAR,
    FIM_DE_JOGO; 

    public static EstadosJogo estadoJogo = MENU;

    
    public static void DefinirEstadoJogo(EstadosJogo estado) { 
        estadoJogo = estado;
    }
}
