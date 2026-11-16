import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class Pong extends JPanel{

    private final int larguraRaquete = 12;
    private final int alturaRaquete = 90;
    private final int tamanhoBola = 18;

    private int raqueteEsquerdaY= 255;
    private boolean pressionandoW=false;
    private boolean pressionandoS=false;

    private int bolaX=391;
    private int bolaY=291;
    private int velocidadeBolaX = 4;
    private int velocidadeBolaY = 4;

    private int pontosEsquerda = 0;
    private int pontosDireita = 0;

    private int raqueteDireitaY = 255;
    private boolean pressionandoCima = false;
    private boolean pressionandoBaixo = false;


    public Pong(){
        setPreferredSize(new Dimension(800,600));
        setBackground(Color.BLACK);

        configurarTeclas();
        
        //Atualiza o jogo a cada 16 milissegundos, aproximadamente 60 vezes por segundo
        Timer timer = new Timer(16, e -> atualizarJogo());
        timer.start();
    }

    private void configurarTeclas(){
        InputMap entradas = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap acoes = getActionMap();

        entradas.put(KeyStroke.getKeyStroke("pressed W"), "pressionouW");
        entradas.put(KeyStroke.getKeyStroke("released W"), "soltouW");
        entradas.put(KeyStroke.getKeyStroke("pressed S"), "pressionouS");
        entradas.put(KeyStroke.getKeyStroke("released S"), "soltouS");
        
        entradas.put(KeyStroke.getKeyStroke("pressed UP"), "pressionouCima");
        entradas.put(KeyStroke.getKeyStroke("released UP"), "soltouCima");
        entradas.put(KeyStroke.getKeyStroke("pressed DOWN"), "pressionouBaixo");
        entradas.put(KeyStroke.getKeyStroke("released DOWN"), "soltouBaixo");
        

        acoes.put("pressionouCima", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pressionandoCima = true;
            }
        });

        acoes.put("soltouCima", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pressionandoCima = false;
            }
        });

        acoes.put("pressionouBaixo", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pressionandoBaixo = true;
             }
        });

        acoes.put("soltouBaixo", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pressionandoBaixo = false;
            }
        });

        acoes.put("pressionouW", new AbstractAction(){
            @Override 
            public void actionPerformed(ActionEvent e){
                pressionandoW = true;
            }
        });

        acoes.put("soltouW", new AbstractAction(){
            @Override 
            public void actionPerformed(ActionEvent e){
                pressionandoW = false;}
        });

        acoes.put("pressionouS", new AbstractAction(){
            @Override 
            public void actionPerformed(ActionEvent e){
                pressionandoS = true;}
        });

        acoes.put("soltouS", new AbstractAction(){
            @Override 
            public void actionPerformed(ActionEvent e){
                pressionandoS = false;}
        });
    }

    private void atualizarJogo(){
        if (pressionandoCima) {
    raqueteDireitaY -= 6;
}

if (pressionandoBaixo) {
    raqueteDireitaY += 6;
}

int limiteInferiorDireita = getHeight() - alturaRaquete;
raqueteDireitaY = Math.max(
    0,
    Math.min(raqueteDireitaY, limiteInferiorDireita)
);
        int velocidade = 6;
        if(pressionandoW){
         raqueteEsquerdaY -= velocidade;
        }
        if(pressionandoS){
            raqueteEsquerdaY += velocidade;
        }
        int limiteInferior = getHeight() - alturaRaquete;
        raqueteEsquerdaY = Math.max(0,Math.min(raqueteEsquerdaY,limiteInferior));

        bolaX += velocidadeBolaX;
        bolaY += velocidadeBolaY;

        if (bolaY <= 0) {
    bolaY = 0;
    if (velocidadeBolaY < 0) {
        velocidadeBolaY = -velocidadeBolaY;
    }
} else if (bolaY + tamanhoBola >= getHeight()) {
    bolaY = getHeight() - tamanhoBola;
    if (velocidadeBolaY > 0) {
        velocidadeBolaY = -velocidadeBolaY;
    }
}

       if (bolaX < 0) {
    pontosDireita++;
    reiniciarBola(-1);
} else if (bolaX > getWidth()) {
    pontosEsquerda++;
    reiniciarBola(1);
}
        int raqueteEsquerdaX = 30;
        int raqueteDireitaX = getWidth() - 30 - larguraRaquete;
        

        boolean bateuNaEsquerda=
        bolaX + tamanhoBola >= raqueteEsquerdaX && bolaX <= raqueteEsquerdaX + larguraRaquete && bolaY + tamanhoBola >= raqueteEsquerdaY && bolaY <= raqueteEsquerdaY + alturaRaquete;
        if(bateuNaEsquerda && velocidadeBolaX < 0){
            velocidadeBolaX = -velocidadeBolaX;

            int centroBola = bolaY + tamanhoBola / 2;
            int centroRaquete = raqueteEsquerdaY + alturaRaquete / 2;
            velocidadeBolaY = (centroBola - centroRaquete) / 8;
        }
        boolean bateuNaDireita=
        bolaX + tamanhoBola >= raqueteDireitaX && bolaX <= raqueteDireitaX + larguraRaquete && bolaY + tamanhoBola >= raqueteDireitaY && bolaY <= raqueteDireitaY + alturaRaquete;
        if(bateuNaDireita && velocidadeBolaX > 0){
            velocidadeBolaX = -velocidadeBolaX;

            int centroBola = bolaY + tamanhoBola / 2;
            int centroRaquete = raqueteDireitaY + alturaRaquete / 2;
            velocidadeBolaY = (centroBola - centroRaquete) / 8;
        }
        
        if(bolaX < 0 || bolaX > getWidth()){
            bolaX = getWidth()/ 2- tamanhoBola / 2;
            bolaY = getHeight() / 2 - tamanhoBola / 2;
            velocidadeBolaX = -velocidadeBolaX;
        }
        repaint();
    }
    
    private void reiniciarBola(int direcao){
        bolaX=getWidth()/ 2 - tamanhoBola / 2;
        bolaY = getHeight() / 2 - tamanhoBola/ 2;

        velocidadeBolaX = Math.abs(velocidadeBolaX)* direcao;
    }
    @Override 
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        //linha centarl
        g.setColor(Color.WHITE);
        for(int y=0; y<getHeight(); y+=30){
            g.fillRect(getWidth()/2 - 2,y,4,15);
        }

        g.setFont(new Font("Arial",Font.BOLD,36));
        g.drawString(String.valueOf(pontosEsquerda),getWidth() / 2 - 60,50);
        g.drawString(String.valueOf(pontosDireita),getWidth() / 2 + 40,50);
        //raquete esquerda
        g.fillRect(30,raqueteEsquerdaY, larguraRaquete, alturaRaquete);
        //raquete direita
        g.fillRect(
    getWidth() - 30 - larguraRaquete,
    raqueteDireitaY,
    larguraRaquete,
    alturaRaquete
);

        //bola
        g.fillOval(bolaX,bolaY,tamanhoBola,tamanhoBola);
    }

   

    public static void main(String[] args) {
        JFrame janela = new JFrame("Pong");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setResizable(true);
        janela.add(new Pong());
        janela.pack();
        janela.setLocationRelativeTo(null);
        janela.setVisible(true);
    }
}