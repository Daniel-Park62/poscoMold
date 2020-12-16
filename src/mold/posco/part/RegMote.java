package mold.posco.part;

import java.util.ArrayList;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import mold.posco.model.MoteConfig;
import mold.posco.model.MoteStatus;

public class RegMote {

	private class ContentProvider implements IStructuredContentProvider {
		/**
		 * 
		 */
		@Override
		public Object[] getElements(Object input) {
			//return new Object[0];
			ArrayList<MoteStatus> arrayList = (ArrayList<MoteStatus>)input;
			return arrayList.toArray();
		}
		@Override
		public void dispose() {
		}
		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}
	
//	List<MoteInfo> selectedTagList = new ArrayList<MoteInfo>();
	Point selectedPoint = new Point(0, 0);
	
	EntityManager em = AppMain.emf.createEntityManager();
	
//	Bundle bundle = FrameworkUtil.getBundle(this.getClass());
    
//    URL url8 = FileLocator.find(bundle, new Path("images/slice_page5.png"), null);
    Image slice_page = new Image(Display.getCurrent(), "images/slice_page5.png");
    
    String sadgb  = "0" ;
    Label lblApActive;
    Label lblApInactive;
    Label lblTagActive;
    Label lblTagInactive;
    Label lblAlertActive;
    Label lblAlertInactive;
    Label lblDate, lblTime;
    
    private Table table;
    private TableViewer tableViewer;
    ArrayList<MoteStatus> tempList ;
    public static final String[] PROPS = { "0","1", "2", "3", "4","5", "6","7","8","9","10","11","STAND" };
    
    VerifyListener numCheck = new VerifyListener() {
		
		@Override
		public void verifyText(VerifyEvent e) {
		       if (e.text.isEmpty()) { 
		           e.doit = true; 
		          } else if (e.keyCode == SWT.ARROW_LEFT || 
		             e.keyCode == SWT.ARROW_RIGHT || 
		             e.keyCode == SWT.BS || 
		             e.keyCode == SWT.DEL || 
		             e.keyCode == SWT.CTRL || 
		             e.keyCode == SWT.SHIFT) { 
		           e.doit = true; 
		          } else { 
		           e.doit = e.text.matches("^-?[0-9]*\\.?[0-9]*") ;
		          } 

		}
	};
    
	public RegMote(Composite parent, int style) {

		ResourceManager resourceManager = new LocalResourceManager(JFaceResources.getResources());

		GridLayout gl_parent = new GridLayout(2, false);
		gl_parent.marginTop = 20;
		gl_parent.horizontalSpacing = 0;
		gl_parent.marginWidth = 0;
		gl_parent.marginHeight = 0;
		gl_parent.verticalSpacing = 0;
		parent.setLayout(gl_parent);
		parent.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		GridLayout gl_composite = new GridLayout(1, false);
		gl_composite.marginWidth = 0;
		gl_composite.marginHeight = 0;
		gl_composite.marginBottom = 5;
		gl_composite.horizontalSpacing = 0;
		gl_composite.verticalSpacing = 0;
		composite.setLayout(gl_composite);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		
		Composite composite_15 = new Composite(composite, SWT.NONE);
		GridData gd_composite_15 = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_composite_15.heightHint = 120;
		composite_15.setLayoutData(gd_composite_15);
		composite_15.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));

		Label lblNewLabel_4 = new Label(composite_15, SWT.NONE);
		lblNewLabel_4.setBounds(0, 0, 1647, 100);
		lblNewLabel_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblNewLabel_4.setImage(slice_page);
/*
		RegMote.addKeyListener(new keyAdapter() { 
			public void KeyPressed(KeyEvent e) {
				if ( e.keyCode == SWT.F15 ) refreshSensorList();  
			}
		});
*/		
		Composite modbutton = new Composite(composite, SWT.NORMAL);
		GridLayout gl_layout = new GridLayout(4, false);
		gl_layout.marginLeft = 15;
		modbutton.setLayout(gl_layout);
		modbutton.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, false, 1, 1));
		modbutton.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		{
			Button b = new Button(modbutton, SWT.ON_TOP);
			b.setFont(SWTResourceManager.getFont( "Consolas", 16, SWT.NORMAL));
			b.setText(" Add ");
			b.setSize(140, 50);
			b.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					NewMoteDlg newmote = new NewMoteDlg(parent.getShell() ) ;
					if (newmote.open() == Window.OK) {
						if (newmote.getMote().getSeq() > 0 ) {
							em.getTransaction().begin();
							em.merge(newmote.getMote());
							em.getTransaction().commit();
							refreshSensorList();
						} else {
							MessageDialog.openError(parent.getShell(), "Mote 등록", "Seq 0 불가합니다.") ;
						}
						
					}
				}
			}); 
		}
		{
			Button b = new Button(modbutton, SWT.ON_TOP);
			b.setFont(SWTResourceManager.getFont("Consolas", 16, SWT.NORMAL));
			b.setText(" Modify ");
			b.setSize(140, 50);
			b.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					int index = tableViewer.getTable().getSelectionIndex() ;
					
					if (index != -1 ) {
						MoteStatus mote = tempList.get(index) ;
						NewMoteDlg newmote = new NewMoteDlg(parent.getShell() , false) ;
						newmote.setMote(mote);
						if (newmote.open() == Window.OK) {
							em.getTransaction().begin();
							if (!em.contains(mote)) {
							    mote = em.merge(mote);
							}
							em.getTransaction().commit();
							MessageDialog.openInformation(parent.getShell(), "Mote 수정", "수정되었습니다.!") ;
							refreshSensorList();
						}

					}
				}
			}); 
		}
		{
			Button b = new Button(modbutton, SWT.ON_TOP);
			b.setFont(SWTResourceManager.getFont( "Consolas", 16, SWT.NORMAL));
			b.setText(" Delete ");
			b.setSize(140, 50);
			b.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					int index = tableViewer.getTable().getSelectionIndex() ;
					
					if (index != -1 ) {
						em.getTransaction().begin();
						MoteStatus mote = tempList.get(index) ;
						if ( MessageDialog.openConfirm(parent.getShell(),"확인", mote.getDesc() + " : 삭제하시겠습니까?"  )) {
							if (!em.contains(mote)) {
							    mote = em.merge(mote);
							}
							em.remove(mote);
							em.getTransaction().commit();
							MessageDialog.openInformation(parent.getShell(), "Mote 삭제", "삭제되었습니다.!") ;
							refreshSensorList();
						}

					}
				}
			}); 
		}

		{
			Button b = new Button(modbutton, SWT.ON_TOP);
			b.setFont(SWTResourceManager.getFont( "Consolas", 16, SWT.NORMAL));
			b.setText(" Out Limit clear ");
			b.setLayoutData(new GridData(SWT.RIGHT,SWT.CENTER,true,true)) ;
			b.setSize(-1, 50);
			b.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					int index = tableViewer.getTable().getSelectionIndex() ;
					
					if (index != -1 ) {
						em.getTransaction().begin();
						MoteStatus mote = tempList.get(index) ;
						if ( MessageDialog.openConfirm(parent.getShell(),"확인", mote.getDesc() + " : Out limit체크를 해제하시겠습니까?"  )) {
							if (!em.contains(mote)) {
							    mote = em.merge(mote);
							}
							mote.setObcnt(0);
							em.getTransaction().commit();
							MessageDialog.openInformation(parent.getShell(),  "Out Limit", "해제되었습니다.!") ;
							refreshSensorList();
						}

					}
				}
			}); 
		}
		
		Composite composite_3 = new Composite(composite, SWT.NONE);
		GridLayout gl_composite_3 = new GridLayout(1, false);
		gl_composite_3.marginRight = 15;
		gl_composite_3.marginLeft = 15;
		gl_composite_3.marginBottom = 5 ;
		composite_3.setLayout(gl_composite_3);
		composite_3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		composite_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));

		tableViewer = new TableViewer(composite_3, SWT.BORDER | SWT.FULL_SELECTION );
		table = tableViewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setFont(AppMain.tblfont1);  // Microsoft Sans Serif
		
		table.setHeaderBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));

		tableViewer.setUseHashlookup(true); 

		TableViewerColumn tvcol = new TableViewerColumn(tableViewer, SWT.NONE | SWT.CENTER);
		tvcol.getColumn().setWidth(0);

		tvcol = new TableViewerColumn(tableViewer, SWT.NONE | SWT.CENTER);
		tvcol.getColumn().setAlignment(SWT.CENTER);
		tvcol.getColumn().setWidth(60);
		tvcol.getColumn().setText("SEQ");

		tvcol = new TableViewerColumn(tableViewer, SWT.NONE);
		tvcol.getColumn().setAlignment(SWT.CENTER);
		tvcol.getColumn().setWidth(120);
		tvcol.getColumn().setText("Status");

		tvcol = new TableViewerColumn(tableViewer, SWT.NONE);
		tvcol.getColumn().setAlignment(SWT.CENTER);
		tvcol.getColumn().setWidth(230);
		tvcol.getColumn().setText("Mac Address");

		tvcol = new TableViewerColumn(tableViewer, SWT.NONE);
		tvcol.getColumn().setAlignment(SWT.CENTER);
		tvcol.getColumn().setWidth(100);
		tvcol.getColumn().setText("Sensor ID");


		tvcol = new TableViewerColumn(tableViewer, SWT.NONE);
		tvcol.getColumn().setAlignment(SWT.NONE);
		tvcol.getColumn().setWidth(250);
		tvcol.getColumn().setText("Description");

		tvcol = new TableViewerColumn(tableViewer, SWT.NONE);
		tvcol.getColumn().setAlignment(SWT.CENTER);
		tvcol.getColumn().setWidth(50);
		tvcol.getColumn().setText("Type");

		tvcol = new TableViewerColumn(tableViewer, SWT.NONE);
		tvcol.getColumn().setAlignment(SWT.CENTER);
		tvcol.getColumn().setWidth(60);
		tvcol.getColumn().setText("Spare");

		tvcol = new TableViewerColumn(tableViewer, SWT.NONE);
		tvcol.getColumn().setAlignment(SWT.CENTER);
		tvcol.getColumn().setWidth(120);
		tvcol.getColumn().setText("배터리설치일");
		
		tvcol = new TableViewerColumn(tableViewer, SWT.NONE);
		tvcol.getColumn().setAlignment(SWT.CENTER | SWT.WRAP);
		tvcol.getColumn().setWidth(80);
		tvcol.getColumn().setText( "장소");

		tvcol = new TableViewerColumn(tableViewer, SWT.NONE);
		tvcol.getColumn().setAlignment(SWT.CENTER);
		tvcol.getColumn().setWidth(80);
		tvcol.getColumn().setText("Mold#");

		tvcol = new TableViewerColumn(tableViewer, SWT.NONE);
		tvcol.getColumn().setAlignment(SWT.CENTER);
		tvcol.getColumn().setWidth(100);
		tvcol.getColumn().setText("설치위치");

		tvcol = new TableViewerColumn(tableViewer, SWT.NONE);
		tvcol.getColumn().setAlignment(SWT.CENTER);
		tvcol.getColumn().setWidth(100);
		tvcol.getColumn().setText("Stand No");

		tableViewer.setContentProvider(new ContentProvider());
		tableViewer.setLabelProvider(new MoteLabelProvider());
		tableViewer.setInput(tempList);
		tableViewer.setColumnProperties(PROPS);
		
	    table.addListener(SWT.MeasureItem,  new Listener() {
	    	@Override
	    	public void handleEvent(Event event) {
	    	event.height = (int)(event.gc.getFontMetrics().getHeight() * 1.5) ;
	    	}

	    });		

	    CellEditor[] editors = new CellEditor[PROPS.length];
	    editors[12] = new TextCellEditor(table);
	    ((Text)editors[12].getControl()).addListener(SWT.Verify , new Listener() {
	    	@Override
	    	public void handleEvent(Event e) {
	    		// validation - mine was for an Integer (also allow 'enter'):

	    		e.doit = "0123456789".indexOf(e.text) >= 0 || e.character == '\0';
	    	}
	    });
	    tableViewer.setCellModifier(new MoteCellModifier(tableViewer));
	    tableViewer.setCellEditors(editors);
	    
		refreshSensorList();
//		table.layout();
		composite_3.layout();
	
		MoteConfig moteConfig  = AppMain.MOTECNF ;

		Composite comp_b = new Composite(composite, SWT.NONE );
		comp_b.setBackground(SWTResourceManager.getColor(250,250,250));
		GridData gd_Composite_2 = new GridData(SWT.FILL, SWT.FILL, true, false);
		gd_Composite_2.heightHint = 150 ;
		gd_Composite_2.minimumHeight = 150 ;
		comp_b.setLayoutData(gd_Composite_2);
//		group_t.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		GridLayout gl_composite_2 = new GridLayout(1, true);
		gl_composite_2.marginTop = 15;
		gl_composite_2.marginLeft = 15;
		gl_composite_2.marginRight = 15;

		comp_b.setLayout(gl_composite_2);

		Group group_t = new Group(comp_b, SWT.NONE );
		group_t.setBackground(SWTResourceManager.getColor(250,250,250));

		group_t.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		group_t.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		GridLayout gl_g = new GridLayout(10, false);
		gl_g.horizontalSpacing = 10;
		gl_g.marginTop = 10;
//		gl_composite_2.marginBottom = 5;

		group_t.setLayout(gl_g);
		group_t.setText("Time Interval & 임계온도/습도 설정");

		Font fontb = SWTResourceManager.getFont( "Consolas", 14, SWT.NORMAL); 
		Label lblsyscode = new Label(group_t, SWT.NONE);
		lblsyscode.setText(" 1.SYS CODE:" +  moteConfig.getSysCode() );
		lblsyscode.setFont(fontb);
		lblsyscode.setAlignment(SWT.RIGHT);
		lblsyscode.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lblsyscode.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblsyscode.setSize(200, -1);

//		Label systext = new Label(group_t, SWT.BORDER );
//		systext.setText( moteConfig.getSysCode() );
//		systext.setFont(fontb);
//		systext.setSize(200, -1);
//		systext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false , true));

		Label lblmeasure = new Label(group_t, SWT.NONE);
		lblmeasure.setText(" 2.Time Interval:");
		lblmeasure.setFont(fontb);
		lblmeasure.setAlignment(SWT.RIGHT);
		lblmeasure.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lblmeasure.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		
	    Spinner spinner = new Spinner(group_t, SWT.BORDER | SWT.CENTER);
	    spinner.setMinimum(1);
	    spinner.setMaximum(60);
	    spinner.setSelection(moteConfig.getMeasure());
	    spinner.setIncrement(1);
	    spinner.setFont(fontb);
	    spinner.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false , true));
		Label lbls = new Label(group_t, SWT.NONE);
		lbls.setText("초");
		lbls.setFont(fontb);
		lbls.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));

		Label lbltemp = new Label(group_t, SWT.NONE);
		lbltemp.setText(" 3.정상온도범위:");
		lbltemp.setFont(fontb);
		lbltemp.setAlignment(SWT.RIGHT);
		lbltemp.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lbltemp.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));

		Text txtMintemp = new Text(group_t, SWT.SINGLE | SWT.BORDER  );
		txtMintemp.setFont(fontb);
		txtMintemp.setLayoutData(new GridData(80,-1));
		txtMintemp.addVerifyListener(numCheck);
	    txtMintemp.setText( Double.toString(moteConfig.getMintemp()) );
		Text txtMaxtemp = new Text(group_t, SWT.SINGLE | SWT.BORDER  );
		txtMaxtemp.setFont(fontb);
	    txtMaxtemp.setLayoutData(new GridData(80,-1));
	    txtMaxtemp.addVerifyListener(numCheck);
	    txtMaxtemp.setText( Double.toString(moteConfig.getMaxtemp()) );

		
		Label lblhumi = new Label(group_t, SWT.NONE);
		lblhumi.setText(" 4.정상습도범위(%):");
		lblhumi.setFont(fontb);
		lblhumi.setAlignment(SWT.RIGHT);
		lblhumi.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lblhumi.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));

		Text txtMinhumi = new Text(group_t, SWT.SINGLE | SWT.BORDER  );
		txtMinhumi.setFont(fontb);
		txtMinhumi.setLayoutData(new GridData(80,-1));
		txtMinhumi.addVerifyListener(numCheck);
	    txtMinhumi.setText( Double.toString(moteConfig.getMinhumi()) );
		Text txtMaxhumi = new Text(group_t, SWT.SINGLE | SWT.BORDER  );
		txtMaxhumi.setFont(fontb);
		txtMaxhumi.setLayoutData(new GridData(80,-1));
		txtMaxhumi.addVerifyListener(numCheck);
	    txtMaxhumi.setText( Double.toString(moteConfig.getMaxhumi()) );

        Group  pGroup = new Group (group_t, SWT.NONE);
        pGroup.setLayout(new RowLayout(SWT.HORIZONTAL));
        pGroup.setFont(SWTResourceManager.getFont( "Consolas", 8, SWT.NORMAL));
        pGroup.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
        pGroup.setText("5. 데이터구분");
        pGroup.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, true, 1, 1));

        Button bp0 = new Button(pGroup, SWT.RADIO);
        bp0.setText("아날로그");
        bp0.setFont(fontb);
        bp0.setSelection(moteConfig.getAdgb().equals("0"));
        Button bp1 = new Button(pGroup, SWT.RADIO);
        bp1.setText("디지탈");
        bp1.setFont(fontb);
        bp1.setSelection(moteConfig.getAdgb().equals("1"));
        bp0.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        bp1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        bp0.pack();
        bp1.pack();

        bp0.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent arg0) {
        		sadgb  = "0" ;
        	}
        });
        bp1.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent arg0) {
        		sadgb  = "1" ;
        	}
        });


		Composite btncontainer = new Composite(group_t, SWT.NONE);
		
		btncontainer.setLayout(new GridLayout(4, false));
		btncontainer.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, true, 9, 1));
		btncontainer.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));

		Label lblpass = new Label(btncontainer, SWT.NONE);
		lblpass.setFont(fontb);
		lblpass.setText(" Password:");
		lblpass.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false , true));
		lblpass.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lblpass.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		
		Text passwordField = new Text(btncontainer, SWT.SINGLE | SWT.BORDER  | SWT.PASSWORD);
	    passwordField.setEchoChar('*');
	    passwordField.setFont(fontb);
	    GridData gd_pss = new GridData(SWT.FILL, GridData.CENTER, false, true);
	    gd_pss.widthHint = 100 ;
	    passwordField.setLayoutData(gd_pss);
		
		{
			Button b = new Button(btncontainer, SWT.PUSH);
			
			b.setFont(SWTResourceManager.getFont( "Calibri", 15, SWT.NORMAL));
			b.setEnabled(false);
			b.setText(" SAVE ");
			b.pack();
			b.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {

					int lmeas = spinner.getSelection()  ;

//					if ( AppMain.sendMeasur( lmeas + "" ) == 1 ) {
//						moteConfig.setSysCode(systext.getText());
						moteConfig.setMeasure( (short) lmeas  ); 
						moteConfig.setMaxhumi( Double.parseDouble(txtMaxhumi.getText()) );
						moteConfig.setMinhumi( Double.parseDouble(txtMinhumi.getText()) );
						moteConfig.setMaxtemp( Double.parseDouble(txtMaxtemp.getText()) );
						moteConfig.setMintemp( Double.parseDouble(txtMintemp.getText()) );
						moteConfig.setAdgb(sadgb);
						em.getTransaction().begin();
						em.merge(moteConfig);

						for (MoteStatus m : tempList) {
							em.merge(m) ;
						}

						em.getTransaction().commit();
						MessageDialog.openInformation(parent.getShell(), "Save Infomation", "수정되었습니다.") ;
//					} else {
//						spinner.setSelection(moteConfig.getMeasure());
//						MessageDialog.openError(parent.getShell(), "Save Infomation", "처리중 오류가 발생하였습니다!") ;
//					}
					AppMain.sendReload();	
					passwordField.setText("");
				}
			}); 
			
			passwordField.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent evt) {
					b.setEnabled( (passwordField.getText().equals("Passw0rd!")) ) ; 
				}
			});
		}		
		{
			Button b = new Button(btncontainer, SWT.PUSH);
			b.setFont(SWTResourceManager.getFont("Calibri", 15, SWT.NORMAL));
			b.setText(" CANCEL ");
			b.pack();
			b.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					refreshSensorList();
//					moteConfig.setSysCode(systext.getText());
					spinner.setSelection(moteConfig.getMeasure());

				}
			}); 
		}
		group_t.layout();
		

	}

	@PreDestroy
	public void preDestroy() {
		
	}


//	@SuppressWarnings("unchecked")
	public void refreshSensorList() {
	    EntityManager em = AppMain.emf.createEntityManager();
		tempList = new ArrayList<MoteStatus>();

		em.clear();
		em.getEntityManagerFactory().getCache().evictAll();
        
        TypedQuery<MoteStatus> qMotes = em.createQuery("select t from MoteStatus t order by t.seq ", MoteStatus.class);

        qMotes.getResultList().stream().forEach( t -> tempList.add(t));
        
		em.close();

		tableViewer.setInput(tempList);
		tableViewer.refresh();

//		}

	}

	private static class MoteLabelProvider implements ITableLabelProvider {
	  /**
	   * Returns the image
	   * 
	   * @param element
	   *            the element
	   * @param columnIndex
	   *            the column index
	   * @return Image
	   */
	
	String[] statusNm = {"Inactive","Sleep","Active"} ;
	  public Image getColumnImage(Object element, int columnIndex) {
	    return null;
	  }

	  /**
	   * Returns the column text
	   * 
	   * @param element
	   *            the element
	   * @param columnIndex
	   *            the column index
	   * @return String
	   */
	  public String getColumnText(Object element, int columnIndex) {
		  MoteStatus mote = (MoteStatus) element;
	    switch (columnIndex) {
	    case 1:
	    	return mote.getSeq()+"";
	    case 2:
	    	return statusNm[mote.getAct()];
	    case 3:
	    	return mote.getMac();
	    case 4:
	    	return mote.getDispNm();
	    case 5:
	    	return mote.getDesc();
	    case 6:
	    	return mote.getGubun() ;
	    case 7:
	    	return mote.getSpare() ;
	    case 8:
	    	return mote.getBattDt().length() < 8 ? mote.getBattDt() + "" : mote.getBattDt().substring(0, 4) + "-" + mote.getBattDt().substring(4, 6) + "-" + mote.getBattDt().substring(6) ;
	    case 9:
	    	return AppMain.PLACE_NM[mote.getPlace()];
	    case 10:
	    	return mote.getMold();
	    case 11:
	    	return AppMain.locname[mote.getLoc()];
	    case 12:
	    	return mote.getStand() + "";
	    }
	    return null;
	  }

	  /**
	   * Adds a listener
	   * 
	   * @param listener
	   *            the listener
	   */
	  public void addListener(ILabelProviderListener listener) {
	    // Ignore it
	  }

	  /**
	   * Disposes any created resources
	   */
	  public void dispose() {
	    // Nothing to dispose
	  }

	  /**
	   * Returns whether altering this property on this element will affect the
	   * label
	   * 
	   * @param element
	   *            the element
	   * @param property
	   *            the property
	   * @return boolean
	   */
	  public boolean isLabelProperty(Object element, String property) {
	    return false;
	  }

	  /**
	   * Removes a listener
	   * 
	   * @param listener
	   *            the listener
	   */
	  public void removeListener(ILabelProviderListener listener) {
	    // Ignore
	  }
	}

	private class MoteCellModifier implements ICellModifier {
		  private Viewer viewer;

		  public MoteCellModifier(Viewer viewer) {
		    this.viewer = viewer;
		  }

		  /**
		   * Returns whether the property can be modified
		   * 
		   * @param element
		   *            the element
		   * @param property
		   *            the property
		   * @return boolean
		   */
		  public boolean canModify(Object element, String property) {
		    // Allow editing of all values
			  if ("STAND".equals(property))
				  return true;
			  else
				  return false;
		  }

		  /**
		   * Returns the value for the property
		   * 
		   * @param element
		   *            the element
		   * @param property
		   *            the property
		   * @return Object
		   */
		  public Object getValue(Object element, String property) {
		    MoteStatus m = (MoteStatus) element;
		    if ("STAND".equals(property))
		      return m.getStand() + "";
		    else
		      return null;
		  }

		  /**
		   * Modifies the element
		   * 
		   * @param element
		   *            the element
		   * @param property
		   *            the property
		   * @param value
		   *            the value
		   */
		  public void modify(Object element, String property, Object value) {
			  if ( value == null || value.equals("")) return ;
		    if (element instanceof Item)
		      element = ((Item) element).getData();

		    MoteStatus m = (MoteStatus) element;
		    if ("STAND".equals(property)) {
		      m.setStand(Short.valueOf( (String) value));
		      em.merge(m);
		    }
		    // Force the viewer to refresh
		    viewer.refresh();
		  }
		}
	
}