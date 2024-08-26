
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTable;

    class App
{
    private  App()
    {        
    }
private static void init()
{
    javax.swing.JMenuBar menubar=new JMenuBar();
    javax.swing.JMenu menu1=new JMenu();
    javax.swing.JMenuItem newgame=new JMenuItem("New Game");
    javax.swing.JMenuItem exitgame= new JMenuItem("Quit");
    menu1.add(exitgame);
    menu1.add(newgame);
    menubar.add(menu1);
    Board b=new Board();
    javax.swing.JScrollPane jt=new javax.swing.JScrollPane(b.getBoard());
    JFrame frame=new JFrame("Jungle!");    
    javax.swing.JLabel lb=new JLabel("It's "+ ( b.getTurn()==Board.Player.RED ? " red's " : " blue's " ) +" turn");
    frame.add(jt);
    frame.add(lb);
    frame.setJMenuBar(menubar);
    frame.setTitle("Jungle");
    frame.pack();
    frame.setVisible(true);
}
public static void main(String[] argc)
{
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
@Override
        public void run() {
            init();
        }
    });
}
}

