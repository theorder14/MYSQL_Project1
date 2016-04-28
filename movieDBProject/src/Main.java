
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import Connection.JDBCCloser;
import Connection.JDBConnection;
import filmController.MyController;
import filmModel.TopModel.MyTopModel;
import filmView.MyMainView;


public class Main extends JFrame implements WindowListener{

	public static void main(String[] args) {
		 
	            new Main();
	}
	public Main() {
		MyMainView view = new MyMainView(this);
		add(view);
		MyTopModel topModel = new MyTopModel();
		MyController cont = new MyController(view, topModel);
		
		addWindowListener(this);
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
	}
	
	@Override
	public void windowActivated(WindowEvent arg0) {

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		JDBCCloser.close(JDBConnection.getJDBCConnection());
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	

}