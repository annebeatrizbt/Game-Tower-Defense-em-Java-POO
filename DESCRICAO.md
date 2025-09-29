DESCRIÇÃO DAS CLASSES

Game - Classe principal que dá inicio ao jogo. Ela abre a janela, carrega a imagem dos sprites e fica responsável por rodar o loop principal, controlando o tempo das atualizações e dos desenhos na tela.
TelaJogo - Responsável por desenhar o cenário e carregar os sprites. Ela monta o fundo do jogo e depois vai servir para desenhar os personagens e outros elementos.
Player - Representa o jogador, com posição (x, y), uma velocidade e a imagem que representa ele. 
Enemy - Representa os inimigos, com lógica própria de movimento. 
InputHandler - É a classe que vai cuidar do teclado. Captura teclas pressionadas e controla o Player.
