package filmController;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;

import filmModel.TopModel.MyTopModel;
import filmModel.TopModel.SqlQry;
import filmView.MyMainView;

public class MyController {

	MyMainView view;
	MyTopModel topModel;
	
	public MyController(MyMainView view, MyTopModel topModel) {
		this.view = view;
		view.addRadioButtonListener(new RadioButtonListener());
		view.addTableMouseListener(new TableMouseListener());
		view.addCRUDButtonsListener(new CRUDButtonsListener());
		view.addSearchButtonListener(new SearchButtonListener());
		
		this.topModel = topModel;
	}
	
	private class RadioButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent aevt) {	
			JRadioButton rObj = (JRadioButton) aevt.getSource();
			if(rObj.getText().equals("Movie")) {
				view.getMainTable().setModel(topModel.fetchData(SqlQry.MOVIE, ""));
			}else if(rObj.getText().equals("Actor")) {
				view.getMainTable().setModel(topModel.fetchData(SqlQry.ACTOR, ""));
			}else {
				view.getMainTable().setModel(topModel.fetchData(SqlQry.GENRE, ""));
			}
			view.getMainTable().addMouseListener(new TableMouseListener());
			view.switchEastPanel(rObj);
		}
		
	}

private class TableMouseListener extends MouseAdapter {
		
		private JTable table;
		private JTextField[] txtFields;
		private JRadioButton rObj;
		public void mouseClicked(MouseEvent mou) {
			
			System.out.println("Table Clicked.");
			rObj = view.getSelectedRb();
			table = view.getMainTable();

			
			if(table.getSelectedRow()!=-1) {
				if(rObj.getText().equals("Movie")) {
					txtFields = view.getTxtFieldsMov();
					updateTxtFields();
				}else if(rObj.getText().equals("Actor")) {
					txtFields = view.getTxtFieldsAct();
					updateTxtFields();
				}else {
					txtFields = view.getTxtFieldsGen();
					updateTxtFields();
				}
			}	
		}
		
		private void updateTxtFields() {
			for(int i=0; i<txtFields.length; i++) 
				txtFields[i].setText(table.getValueAt(table.getSelectedRow(),i).toString());
		}	
	}
private class SearchButtonListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent ae) {
		JRadioButton rObj = view.getSelectedRb();
		String searchStr = view.getSearchField().getText();
		if(rObj.getText().equals("Movie")) {
			view.getMainTable().setModel(topModel.fetchData(SqlQry.MOVIE, searchStr));
		}else if(rObj.getText().equals("Actor")) {
			view.getMainTable().setModel(topModel.fetchData(SqlQry.ACTOR, searchStr));
		}else {
			view.getMainTable().setModel(topModel.fetchData(SqlQry.GENRE, searchStr));
		}
		view.getMainTable().addMouseListener(new TableMouseListener());
	}
	
}

	private class CRUDButtonsListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent cr) {
			// TODO Auto-generated method stub
			JButton bObj = (JButton) cr.getSource();
			JRadioButton rObj = view.getSelectedRb();
				
			if(bObj.getText().equals("ADD")) {
				if(addConfirm()) {
					if(rObj.getText().equals("Movie")) {
						addDialog(topModel.addData(SqlQry.MOVIE, 
							getData(view.getTxtFieldsMov(), view.getTxtFieldsMov().length)));
					}else if(rObj.getText().equals("Actor")) {
						addDialog(topModel.addData(SqlQry.ACTOR,
								getData(view.getTxtFieldsAct(), view.getTxtFieldsAct().length)));		
					}else {
						addDialog(topModel.addData(SqlQry.GENRE,
							getData(view.getTxtFieldsGen(), view.getTxtFieldsGen().length)));	
					}
				}else {
					JOptionPane.showMessageDialog(null, "Data was not added");
				}
			}else if(bObj.getText().equals("UPDATE")) {
				if(updateConfirm()) {
					if(rObj.getText().equals("Movie")) {
						updateDialog(topModel.updateData(SqlQry.MOVIE,
								getData(view.getTxtFieldsMov(), view.getTxtFieldsMov().length)));
					}else if(rObj.getText().equals("Actor")) {
						updateDialog(topModel.updateData(SqlQry.ACTOR,
								getData(view.getTxtFieldsAct(), view.getTxtFieldsAct().length)));	
					}else {
						updateDialog(topModel.updateData(SqlQry.GENRE,
								getData(view.getTxtFieldsGen(), view.getTxtFieldsGen().length)));	
					}
				}else {
					JOptionPane.showMessageDialog(null, "Data was not updated");
				}
			}else {
				if(deleteConfirm()) {
					if(rObj.getText().equals("Movie")) {
						deleteDialog(topModel.deleteData(SqlQry.MOVIE, 
							getData(view.getTxtFieldsMov(), view.getTxtFieldsMov().length)));
					}else if(rObj.getText().equals("Actor")) {
						deleteDialog(topModel.deleteData(SqlQry.ACTOR,
							getData(view.getTxtFieldsAct(), view.getTxtFieldsAct().length)));
					}else {
						deleteDialog(topModel.deleteData(SqlQry.GENRE, 
								getData(view.getTxtFieldsGen(), view.getTxtFieldsGen().length)));
					}
				}else {
					JOptionPane.showMessageDialog(null, "Data was not deleted");
				}
			}
			view.refreshView();
		}
		
		private String[] getData(JTextField[] txtFields, int numOfStuff) {
			String[] stuff = new String[numOfStuff];
			for(int i=0;i<stuff.length;i++)
				stuff[i] = txtFields[i].getText();
			return stuff;
		}
		private void addDialog(boolean bool) {
			if(bool)
				JOptionPane.showMessageDialog(null, "Data added");
			else
				JOptionPane.showMessageDialog(null, "Data was not added");
		}
		private void updateDialog(boolean bool) {
			if(bool)
				JOptionPane.showMessageDialog(null, "Data updated");
			else
				JOptionPane.showMessageDialog(null, "Data was not updated");
		}
		private void deleteDialog(boolean bool) {
			if(bool)
				JOptionPane.showMessageDialog(null, "Data deleted");
			else
				JOptionPane.showMessageDialog(null, "Data was not deleted");
		}
		private boolean deleteConfirm() {
			Object[] options = {"Yes", "NO"};
			int answer = JOptionPane.showOptionDialog(null,
				"Delete data?",
				"CONFIRM DELETE",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.WARNING_MESSAGE,
				null,
				options,
				options[1]);
			if(answer==0) return true;
			else return false;
		}
		private boolean updateConfirm() {
			Object[] options = {"Yes", "NO"};
			int answer = JOptionPane.showOptionDialog(null,
				"Update data?",
				"CONFIRM Update",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.WARNING_MESSAGE,
				null,
				options,
				options[1]); 
			if(answer==0) return true;
			else return false;
		}
		private boolean addConfirm() {
			Object[] options = {"Yes", "NO"};
			int answer = JOptionPane.showOptionDialog(null,
				"Add data?",
				"CONFIRM ADD",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.WARNING_MESSAGE,
				null,
				options,
				options[1]); 
			if(answer==0) return true;
			else return false;
		}
	}
	
}	
