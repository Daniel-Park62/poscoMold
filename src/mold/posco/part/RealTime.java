package mold.posco.part;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.wb.swt.SWTResourceManager;

import com.ibm.icu.util.Calendar;

import mold.posco.model.FindMoteInfo;
import mold.posco.model.Vsensordata;

public class RealTime  {

    EntityManager em = AppMain.emf.createEntityManager();
    FindMoteInfo findMoteinfo = new FindMoteInfo() ;
    Composite parent ;
    public RealTime(Composite parent, int style) {
    	this.parent = parent ;
		postConstruct(parent);
	}


	private static class ContentProvider implements IStructuredContentProvider {
		@Override
		public Object[] getElements(Object input) {
			//return new Object[0];
			return ((ArrayList<Vsensordata>)input).toArray();
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
	
	HashMap<Integer, Integer> tagCount = new HashMap<Integer, Integer>();

    Image slice_page3 = new Image(Display.getCurrent(),"images/slice_page3.png");
    
//    URL url9 = FileLocator.find(bundle, new Path("images/categoryicon_1.png"), null);
//    ImageDescriptor categoryicon_1 = ImageDescriptor.createFromURL(url9);

//    URL url10 = FileLocator.find(bundle, new Path("images/categoryicon_2.png"), null);
//    ImageDescriptor categoryicon_2 = ImageDescriptor.createFromURL(url10);

//    URL url11 = FileLocator.find(bundle, new Path("images/categoryicon_3.png"), null);
//    ImageDescriptor categoryicon_3 = ImageDescriptor.createFromURL(url11);

    Label lblApActive;
    Label lblApInactive;
    Label lblTagActive;
    Label lblTagInactive;
    Label lblAlertActive;
    Label lblAlertInactive;
    Label lblDate, lblTime;
    Label lblinterval ;
    private Label bottoml ;
    private Table table;
    private TableViewer tableViewer;
    private DateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private DateFormat dateFmt1 = new SimpleDateFormat("yyyy-MM-dd");
    private DateFormat timeFmt = new SimpleDateFormat("HH:mm:ss");
    private Combo cbddown , cbdplace, cbdstat ;

    private Cursor curc ;
    private String[] slist, slistA ;
    private short qplace = 0 ;
    @PostConstruct
	public void postConstruct(Composite parent) {
		
		curc = parent.getCursor() ;

	    Color COLOR_T = SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT) ;
		GridLayout gl_parent = new GridLayout(1, false);
		gl_parent.marginTop = 0;
		gl_parent.horizontalSpacing = 0;
		gl_parent.marginWidth = 0;
		gl_parent.marginHeight = 0;
		gl_parent.verticalSpacing = 0;
		gl_parent.marginBottom = 10;
		parent.setLayout(gl_parent);
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		GridLayout gl_composite = new GridLayout(1, false);
		gl_composite.marginWidth = 0;
		gl_composite.marginHeight = 0;
		gl_composite.horizontalSpacing = 0;
		gl_composite.verticalSpacing = 0;
		composite.setLayout(gl_composite);
		composite.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		
		Composite composite_15 = new Composite(composite, SWT.NONE);
		composite_15.setLayout(new GridLayout(1,true));
		GridData gd_composite_15 = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_composite_15.heightHint = 150;
		composite_15.setLayoutData(gd_composite_15);
		composite_15.setBackground(COLOR_T);
//		{
//			Label lbl = new Label(composite_15, SWT.NONE) ;
//			lbl.setText("���� KR IMPELLER ���� �µ� ����͸�");
//			lbl.setBackground(COLOR_T);
//			lbl.setFont(AppMain.font1);
//		}
//		composite_15.setBackgroundImage(slice_page3);
		
		
		Label lblNewLabel_4 = new Label(composite_15, SWT.NONE);
		lblNewLabel_4.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
//		lblNewLabel_4.setBounds(0, 0, 1647, 600);
		lblNewLabel_4.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TRANSPARENT));
		lblNewLabel_4.setImage(slice_page3);
		
		
		Label label = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gd_sep = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_sep.heightHint = 20;
		label.setBackground(COLOR_T);
		label.setLayoutData(gd_sep);
		
		Composite composite_2 = new Composite(composite, SWT.NONE);
		GridLayout gl_in = new GridLayout(15,false);
		gl_in.marginRight = 15;
		gl_in.marginLeft = 10;
		gl_in.horizontalSpacing = 10 ;
		composite_2.setLayout(gl_in);
		GridData gd_in = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_in.heightHint = 50;

		composite_2.setLayoutData(gd_in);
		composite_2.setBackground(COLOR_T);

/*		
        Group  pGroup = new Group (composite_2, SWT.NONE);
        pGroup.setLayout(new RowLayout(SWT.HORIZONTAL));
        pGroup.setFont(SWTResourceManager.getFont( "", 1, SWT.NORMAL));
        pGroup.setBackground(COLOR_T);
		
		{
        	Button bp0 = new Button(pGroup, SWT.RADIO);
        	bp0.setText("공장");
        	bp0.setFont(AppMain.font2);
        	bp0.setSelection(true);
        	Button bp1 = new Button(pGroup, SWT.RADIO);
        	bp1.setText("수리장");
        	bp1.setFont(AppMain.font2);
        	bp0.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        	bp1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        	bp0.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					qplace = 0 ;
				}
			});
        	bp1.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					qplace = 1 ;
				}
			});
		}
*/		
			Label lbl1 = new Label(composite_2, SWT.NONE) ;
			lbl1.setText(" *장소");
			lbl1.setBackground(COLOR_T);
			lbl1.setFont(AppMain.font1);
			lbl1.pack();
			cbdplace = new Combo(composite_2, SWT.DROP_DOWN | SWT.BORDER);
			cbdplace.setFont(AppMain.font2);

			cbdplace.setItems(new String[] {"공장","수리장","ALL"} );
			cbdplace.select(2);
			cbdplace.pack();

			
			Label lbl = new Label(composite_2, SWT.NONE) ;
			lbl.setText("*ID");
			lbl.setBackground(COLOR_T);
			lbl.setFont(AppMain.font1);
			lbl.pack();

		slist = findMoteinfo.getSensorList() ;
		slistA = new String[slist.length + 1] ;
		
		System.arraycopy(new String[] {"ALL"}, 0, slistA, 0, 1);
		System.arraycopy(slist,0,slistA, 1, slist.length ) ;

		cbddown = new Combo(composite_2, SWT.DROP_DOWN | SWT.BORDER);
		cbddown.setFont(AppMain.font2);

		cbddown.setItems(slistA);
		cbddown.select(0);
		cbddown.pack();
		
		{
			Label lblf = new Label(composite_2, SWT.NONE) ;
			lblf.setText(" *조회기간 Date/Time");
			lblf.setBackground(COLOR_T);
			lblf.setFont(AppMain.font1);
			lblf.pack();
		}
		GridData gdinput = new GridData(100,20);
		DateText fromDate = new DateText(composite_2, SWT.SINGLE | SWT.BORDER | SWT.CENTER  );
		fromDate.setLayoutData(gdinput);
		fromDate.setFont(AppMain.font2);
		TimeText fromTm = new TimeText(composite_2, SWT.SINGLE | SWT.BORDER | SWT.CENTER );
		fromTm.setLayoutData(new GridData(80, 20));
		fromTm.setFont(AppMain.font2);
		{
			Label lblt = new Label(composite_2, SWT.NONE) ;
			lblt.setText("~");
			lblt.setBackground(COLOR_T);
			lblt.setFont(AppMain.font1);
			lblt.pack();
		}
		DateText toDate = new DateText(composite_2, SWT.SINGLE | SWT.BORDER | SWT.CENTER );
		toDate.setLayoutData(gdinput);
		toDate.setFont(AppMain.font2);
		TimeText toTm = new TimeText(composite_2, SWT.SINGLE | SWT.BORDER | SWT.CENTER );
		toTm.setLayoutData(new GridData(80, 20));
		toTm.setFont(AppMain.font2);

        Group  rGroup = new Group (composite_2, SWT.NONE);
        rGroup.setLayout(new RowLayout(SWT.HORIZONTAL));
        rGroup.setFont(SWTResourceManager.getFont(  "", 1, SWT.NORMAL));
        rGroup.setBackground(COLOR_T);
//        rGroup.setSize(300, -1);
        {
//        	Button b10 = new Button(rGroup, SWT.RADIO);
//        	b10.setText("10분 ");
//        	b10.setFont(AppMain.font2);
        	Button b30 = new Button(rGroup, SWT.RADIO);
        	b30.setText("30분 ");
        	b30.setFont(AppMain.font2);
        	Button b1 = new Button(rGroup, SWT.RADIO);
        	b1.setText("1시간 ");
        	b1.setFont(AppMain.font2);
        	Button b2 = new Button(rGroup, SWT.RADIO);
        	b2.setText("2시간 ");
        	b2.setFont(AppMain.font2);
        	b1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        	b2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        	b30.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
//        	b10.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

        	b2.addSelectionListener(new SelectionAdapter() {
    			@Override
    			public void widgetSelected(SelectionEvent e) {
    				Timestamp todt = findMoteinfo.getLasTime() ;
    				
    				Calendar cal = Calendar.getInstance();
    				cal.setTimeInMillis(todt.getTime()); 
    				cal.add(Calendar.SECOND, -7200); 
    				
    				Timestamp fmdt = new Timestamp(cal.getTime().getTime());
    				fromDate.setText(dateFmt1.format(fmdt ) );
    				fromTm.setText(timeFmt.format(fmdt ) );
    				toDate.setText(dateFmt1.format(todt ) );
    				toTm.setText(timeFmt.format(todt ) );
    				
    			}
			});
        	b1.addSelectionListener(new SelectionAdapter() {
    			@Override
    			public void widgetSelected(SelectionEvent e) {
    				Timestamp todt = findMoteinfo.getLasTime() ;
    				
    				Calendar cal = Calendar.getInstance();
    				cal.setTimeInMillis(todt.getTime()); 
    				cal.add(Calendar.SECOND, -3600); 
    				
    				Timestamp fmdt = new Timestamp(cal.getTime().getTime());
    				fromDate.setText(dateFmt1.format(fmdt ) );
    				fromTm.setText(timeFmt.format(fmdt ) );
    				toDate.setText(dateFmt1.format(todt ) );
    				toTm.setText(timeFmt.format(todt ) );
    				
    			}
			});
        	b30.addSelectionListener(new SelectionAdapter() {
    			@Override
    			public void widgetSelected(SelectionEvent e) {
    				Timestamp todt = findMoteinfo.getLasTime() ;
    				
    				Calendar cal = Calendar.getInstance();
    				cal.setTimeInMillis(todt.getTime()); 
    				cal.add(Calendar.SECOND, -1800); 
    				
    				Timestamp fmdt = new Timestamp(cal.getTime().getTime());
    				fromDate.setText(dateFmt1.format(fmdt ) );
    				fromTm.setText(timeFmt.format(fmdt ) );
    				toDate.setText(dateFmt1.format(todt ) );
    				toTm.setText(timeFmt.format(todt ) );

    			}

			});
        	
//        	b10.addSelectionListener(new SelectionAdapter() {
//    			@Override
//    			public void widgetSelected(SelectionEvent e) {
//    				Timestamp todt = findMoteinfo.getLasTime() ;
//    				
//    				Calendar cal = Calendar.getInstance();
//    				cal.setTimeInMillis(todt.getTime()); 
//    				cal.add(Calendar.SECOND, -600); 
//    				
//    				Timestamp fmdt = new Timestamp(cal.getTime().getTime());
//    				fromDate.setText(dateFmt1.format(fmdt ) );
//    				fromTm.setText(timeFmt.format(fmdt ) );
//    				toDate.setText(dateFmt1.format(todt ) );
//    				toTm.setText(timeFmt.format(todt ) );
//    				
//    			}
//
//			});
//
        	b30.setSelection(true);
        	
        }
//        {
//            Group  sGroup = new Group (composite_2, SWT.NONE);
//            sGroup.setLayout(new RowLayout(SWT.HORIZONTAL));
//            sGroup.setFont(SWTResourceManager.getFont(  "", 1, SWT.NORMAL));
//            sGroup.setBackground(COLOR_T);
//        	bs0 = new Button(sGroup, SWT.CHECK);
//        	bs0.setText("정상 ");
//        	bs0.setFont(AppMain.font2);
//        	bs1 = new Button(sGroup, SWT.CHECK);
//        	bs1.setText("L/B ");
//        	bs1.setFont(AppMain.font2);
//        	bs2 = new Button(sGroup, SWT.CHECK);
//        	bs2.setText("O/B ");
//        	bs2.setFont(AppMain.font2);
//            bs0.setSelection(true);
//            bs1.setSelection(true);
//            bs2.setSelection(true);
//        	bs0.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
//        	bs1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
//        	bs2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
//
//        }
        Label lbls = new Label(composite_2, SWT.NONE) ;
        lbls.setText("*상태");
        lbls.setBackground(COLOR_T);
        lbls.setFont(AppMain.font1);
		
		cbdstat = new Combo(composite_2, SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY );
		cbdstat.setFont(AppMain.font2);

		cbdstat.setItems(new String[] {"ALL", "정상", "O/B", "L/B"});
		cbdstat.select(0);
		cbdstat.pack();

        {
			Button b = new Button(composite_2, SWT.PUSH);
			b.setFont(AppMain.font1);
			b.setText(" 검색 ");
			b.pack();
			b.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					String sfrom = fromDate.getText() + " " + fromTm.getText() ;
					String sto = toDate.getText() + " " + toTm.getText() ;
					Timestamp stime, etime ;
					try {
						stime = Timestamp.valueOf(sfrom) ;
						etime = Timestamp.valueOf(sto) ;
					} catch (Exception e2) {
						MessageDialog.openError(parent.getShell(), "날짜오류", "날짜 입력을 바르게하세요.") ;
						return ;
					}
					if (cbddown.getSelectionIndex() == 0  && ( etime.getTime() - stime.getTime() ) / ( 1000 * 3600 * 24 ) > 6 ) {
						MessageDialog.openError(parent.getShell(), "범위오류", "Sensor ALL인 경우 조회기간은 일주일이내로 하세요.") ;
						return ;
					}
					
					retriveData(sfrom, sto);
				}
			}); 
		}

		{
			Button b = new Button(composite_2, SWT.PUSH);
			b.setFont(AppMain.font1b);
			b.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE));
			b.setText(" 파일저장 ");
			b.pack();
			b.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					AppMain.exportTable(tableViewer, 1);
				}
			}); 
 
		}
		Composite composite_3 = new Composite(composite, SWT.NONE);
		GridLayout gl_composite_3 = new GridLayout(1, false);
		gl_composite_3.marginRight = 20;
		gl_composite_3.marginLeft = 10;
//		gl_composite_3.marginBottom = 5;
		
		composite_3.setLayout(gl_composite_3);
		composite_3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		composite_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));

		tableViewer = new TableViewer(composite_3, SWT.BORDER | SWT.FULL_SELECTION |SWT.VIRTUAL );
		
		tableViewer.setUseHashlookup(true);
		

		bottoml = new Label(composite, SWT.NONE) ;
		GridData gd_blabel = new GridData(SWT.CENTER, SWT.CENTER, true, false );
		gd_blabel.heightHint = 50 ;
		bottoml.setLayoutData(gd_blabel);
		bottoml.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		bottoml.setFont(AppMain.tblfont1);
//		bottoml.setSize(500, 30);

		
		table = tableViewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setFont(AppMain.tblfont1);
		table.setHeaderBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		
		TableViewerColumn tvc = new TableViewerColumn(tableViewer, SWT.NONE);
		tvc.setLabelProvider(new ColumnLabelProvider() {	});

		tvc = new TableViewerColumn(tableViewer, SWT.NONE);
		tvc.setLabelProvider(new ColumnLabelProvider() {
			public String getText(Object element) {
				
				return element == null ? "" : dateFmt1.format( ((Vsensordata)element).getTm()) ;
			}
		});
		
		TableColumn tblclmnNewColumn2 = tvc.getColumn();
		tblclmnNewColumn2.setAlignment(SWT.CENTER);
		tblclmnNewColumn2.setWidth(150);
		tblclmnNewColumn2.setText("Date");

		tvc = new TableViewerColumn(tableViewer, SWT.NONE);
		tvc.setLabelProvider(new ColumnLabelProvider() {
			public String getText(Object element) {
				return element == null ? "" :  timeFmt.format( ((Vsensordata)element).getTm()) ;
			}
		});
		TableColumn tcl_time = tvc.getColumn();
		tcl_time.setAlignment(SWT.CENTER);
		tcl_time.setWidth(150);
		tcl_time.setText("Time");

		tvc = new TableViewerColumn(tableViewer, SWT.NONE);
		tvc.setLabelProvider(new ColumnLabelProvider() {
			public String getText(Object element) {
				
				return element == null ? "" :((Vsensordata)element).getDispNm() ;
			}
		});

		TableColumn tblclmnNewColumn1 = tvc.getColumn();
		tblclmnNewColumn1.setAlignment(SWT.CENTER);
		tblclmnNewColumn1.setWidth(80);
		tblclmnNewColumn1.setText("ID");
		
		tvc = new TableViewerColumn(tableViewer, SWT.NONE);
		tvc.setLabelProvider(new ColumnLabelProvider() {
			public String getText(Object element) {
				
				if (element == null) return "";
				switch (((Vsensordata)element).getAct()) {
				case 2:
					return "Active";

				case 1:
					return "Waiting";

				default:
					return "Inactive";
				}
			}
		});
		
		TableColumn tblclmnNewColumn3 = tvc.getColumn();
		tblclmnNewColumn3.setAlignment(SWT.CENTER);
		tblclmnNewColumn3.setWidth(100);
		tblclmnNewColumn3.setText("Status");

		tvc = new TableViewerColumn(tableViewer, SWT.NONE);
		tvc.setLabelProvider(new ColumnLabelProvider() {

			public String getText(Object element) {
				if (element == null) return "";
				return String.format("%,2.2f", ((Vsensordata)element).getTemp() ) ;
			}
		});
		TableColumn tblclmnNewColumn4 = tvc.getColumn();
		tblclmnNewColumn4.setAlignment(SWT.CENTER);
		tblclmnNewColumn4.setWidth(100);
		tblclmnNewColumn4.setText("온도");
		
		tvc = new TableViewerColumn(tableViewer, SWT.NONE);
		tvc.setLabelProvider(new ColumnLabelProvider() {
			public String getText(Object element) {
				if (element == null)  return "";
				return String.format("%,2.2f", ((Vsensordata)element).getHumi() ) ;
			}
		});
		TableColumn tc_humi = tvc.getColumn();
		tc_humi.setAlignment(SWT.CENTER);
		tc_humi.setWidth(100);
		tc_humi.setText("습도");

		tvc = new TableViewerColumn(tableViewer, SWT.NONE);
		tvc.setLabelProvider(new ColumnLabelProvider() {
			public String getText(Object element) {
				if (element == null) return "" ;

				return AppMain.PLACE_NM[ ((Vsensordata)element).getPlace() ] ;
			}
		});

		TableColumn tcl_place = tvc.getColumn() ;
		tcl_place.setText("장소");
		tcl_place.setAlignment(SWT.CENTER);
		tcl_place.setWidth(80);

		tvc = new TableViewerColumn(tableViewer, SWT.NONE);
		tvc.setLabelProvider(new ColumnLabelProvider() {
			public String getText(Object element) {
				return element == null ? "" : ((Vsensordata)element).getMold()  ;
			}
		});
		TableColumn tcmold = tvc.getColumn();
		tcmold.setAlignment(SWT.CENTER);
		tcmold.setWidth(80);
		tcmold.setText("Mold#");

		tvc = new TableViewerColumn(tableViewer, SWT.NONE);
		tvc.setLabelProvider(new ColumnLabelProvider() {
			public String getText(Object element) {
				if (element == null) return "";
				return AppMain.locname[ ((Vsensordata)element).getLoc() ];
			}
		});
		TableColumn tblclmnNewColumn7 = tvc.getColumn();
		tblclmnNewColumn7.setAlignment(SWT.CENTER);
		tblclmnNewColumn7.setWidth(100);
		tblclmnNewColumn7.setText("위치");

		tvc = new TableViewerColumn(tableViewer, SWT.NONE);
		tvc.setLabelProvider(new ColumnLabelProvider() {
			public String getText(Object e) {
				if (e == null) return "" ;
				String str = "" ;
				if (((Vsensordata)e).getOutb() == 1 )  str = "O/B " ;
				if (((Vsensordata)e).getLow() == 1 )  str += "L/B" ;
				return str.trim().replace(" ", ",") ;
			}
		});
		TableColumn tcl8 = tvc.getColumn();
		tcl8.setAlignment(SWT.CENTER);
		tcl8.setWidth(150);
		tcl8.setText("상태");

		tableViewer.setContentProvider(new ContentProvider());

//		Timestamp time_c = em.createQuery("select max(t.tm) from MoteInfo t", Timestamp.class).getSingleResult() ;
//		todt = em.createQuery("select t.lastm from LasTime t ", Timestamp.class).getSingleResult() ;
		Timestamp todt = findMoteinfo.getLasTime() ;
		
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(todt.getTime()); 
		cal.add(Calendar.SECOND, -600); 
		
		Timestamp fmdt = new Timestamp(cal.getTime().getTime());
		fromDate.setText(dateFmt1.format(fmdt ) );
		fromTm.setText(timeFmt.format(fmdt ) );
		toDate.setText(dateFmt1.format(todt ) );
		toTm.setText(timeFmt.format(todt ) );

		int sno = 0 ;
		if  (cbddown.getSelectionIndex() > 0) sno = Integer.parseInt(cbddown.getText().substring(2)) ;
		tableViewer.setInput(getMoteHists( fmdt, todt, sno, qplace ));
//		tableViewer.refresh();
		bottoml.setText(String.format("%,d 건",tableViewer.getTable().getItemCount()) );
		bottoml.pack();	

	}

	private void retriveData(String sfrom, String sto ) {
		Timestamp tfrom, tto ;
		try {
			tfrom = Timestamp.valueOf(sfrom) ;
			tto = Timestamp.valueOf(sto) ;
		} catch (Exception e2) {
			MessageDialog.openError(parent.getShell(), "날짜오류", "날짜 입력을 바르게하세요.") ;
			return ;
		}
		
		int sno = 0 ;
		if  (cbddown.getSelectionIndex() > 0) sno = findMoteinfo.getMoteSeq(cbddown.getText()) ;
		parent.getShell().setCursor( AppMain.busyc);
		
		tableViewer.setInput(getMoteHists( tfrom, tto, sno, (short)cbdplace.getSelectionIndex() ));
//		tableViewer.refresh();
		bottoml.setText(String.format("%,d 건",tableViewer.getTable().getItemCount()) );
		bottoml.pack();	
		parent.getShell().setCursor( curc);

	}

    private ArrayList<Vsensordata> getMoteHists(Timestamp fmdt, Timestamp todt, int seq, short place) {

		ArrayList<Vsensordata> tempList2 = new ArrayList<Vsensordata>();
		EntityManager em = AppMain.emf.createEntityManager();
		em.clear();
		em.getEntityManagerFactory().getCache().evictAll();
		
		String sseqif = "" ;
		if (seq != 0) {
			sseqif = "and t.seq = " + seq ;
		}
		if (cbdstat.getSelectionIndex() == 1)
			sseqif += " and t.low = 0 and t.outb = 0" ;
		else if (cbdstat.getSelectionIndex() == 2)
			sseqif += " and t.outb = 1" ;
		else if (cbdstat.getSelectionIndex() == 3)
			sseqif += " and t.low = 1" ;
			
		if (place != 2) sseqif += " and t.place = " + place ;
        TypedQuery<Vsensordata> qMotes = em.createQuery("select t from Vsensordata t " 
        		+ "where t.tm between :fmdt and :todt " + sseqif + " order by t.tm desc ,t.seq asc", Vsensordata.class);

        qMotes.setParameter("fmdt", fmdt);
        qMotes.setParameter("todt", todt);
        qMotes.setHint(QueryHints.READ_ONLY  , HintValues.TRUE);
//        qMotes.setHint(QueryHints.CACHE_USAGE , CacheUsage.CheckCacheOnly);
        qMotes.getResultList().stream().forEach( t -> tempList2.add(t));

		em.close();

		return tempList2 ;

    }
	
}
