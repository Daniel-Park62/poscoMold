package mold.posco.part;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.wb.swt.SWTResourceManager;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import mold.posco.model.MoteConfig;
import mold.posco.model.MoteInfo;
import mold.posco.model.MoteStatus;

@SuppressWarnings("unchecked")
public class DashBoard2 {

	private static class ContentProvider_1 implements IStructuredContentProvider {
		@Override
		public Object[] getElements(Object input) {
			return ((ArrayList<MoteStatus>)input).toArray();
		}
		@Override
		public void dispose() {
		}
		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}
	private static class ContentProvider implements IStructuredContentProvider {
		@Override
		public Object[] getElements(Object input) {
			//return new Object[0];
			return ((ArrayList<MoteInfo>)input).toArray();
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
	HashMap<Integer, Integer> tagCount = new HashMap<Integer, Integer>();
	Bundle bundle = FrameworkUtil.getBundle(this.getClass());
    // use the org.eclipse.core.runtime.Path as import
    
//    URL url8 = FileLocator.find(bundle, new Path("images/slice_page1.png"), null);
//    ImageDescriptor slice_page1 = ImageDescriptor.createFromURL(url8);
	Image slice_page1 = SWTResourceManager.getImage("images/slice_page1.png");
	
    Label lblApActive;
    Label lblApInactive;
    Label lblTagActive;
    Label lblTagInactive;
    Label lblAlertActive;
    Label lblAlertInactive;
    Label lblDate, lblTime;
    Label lblinterval;
    
    private Table table;
    private TableViewer tableViewer;
    private TableViewer tableViewer_1;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    DateFormat dateFmt2 = new SimpleDateFormat("HH:mm:ss");
    Font font2 = SWTResourceManager.getFont("Calibri", 14, SWT.NORMAL);

    Thread uiUpdateThread ;
    
	public DashBoard2(Composite parent, int style) {

//		super(parent, style) ;
		
		GridLayout gl_parent = new GridLayout(1, false);
		gl_parent.marginTop = 10;
		gl_parent.horizontalSpacing = 0;
		gl_parent.marginWidth = 0;
		gl_parent.marginHeight = 0;
		gl_parent.verticalSpacing = 0;
		parent.setLayout(gl_parent);
	
		Composite composite = new Composite(parent, SWT.NONE);
		
		composite.addDisposeListener(new DisposeListener() {
			
			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				uiUpdateThread.stop();
			}
		});
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		GridLayout gl_composite = new GridLayout(1, false);
		gl_composite.marginWidth = 0;
		gl_composite.marginHeight = 0;
		gl_composite.horizontalSpacing = 0;
		gl_composite.verticalSpacing = 0;
		composite.setLayout(gl_composite);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		Composite composite_15 = new Composite(composite, SWT.NONE);
		GridData gd_composite_15 = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_composite_15.heightHint = 420;
		composite_15.setLayoutData(gd_composite_15);
		composite_15.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		composite_15.setBackgroundMode(SWT.INHERIT_DEFAULT);

		
		lblApActive = new Label(composite_15, SWT.NONE);
		lblApActive.setAlignment(SWT.RIGHT);
		lblApActive.setFont(AppMain.fontT);
		lblApActive.setBounds(295, 170, 40, 30);
		lblApActive.setText("99");
		
		lblApInactive = new Label(composite_15, SWT.NONE);
		lblApInactive.setAlignment(SWT.RIGHT);
		lblApInactive.setFont(AppMain.fontT);
		lblApInactive.setBounds(295, 205, 40, 30);
		lblApInactive.setText("99");
		
		lblTagActive = new Label(composite_15, SWT.NONE);
		lblTagActive.setAlignment(SWT.RIGHT);
		lblTagActive.setFont(AppMain.fontT);
		lblTagActive.setBounds(665, 170, 40, 30);
		lblTagActive.setText("99");
		
		lblTagInactive = new Label(composite_15, SWT.NONE);
		lblTagInactive.setAlignment(SWT.RIGHT);
		lblTagInactive.setFont(AppMain.fontT);
		lblTagInactive.setBounds(665, 205, 40, 30);
		lblTagInactive.setText(" 0");
		
		lblAlertActive = new Label(composite_15, SWT.NONE);
		lblAlertActive.setAlignment(SWT.RIGHT);
		lblAlertActive.setFont(AppMain.fontT);
		lblAlertActive.setBounds(1045, 170, 40, 30);
		lblAlertActive.setText(" 0");
		lblAlertActive.setBackground(SWTResourceManager.getColor( 255,240,240));
		
		lblAlertInactive = new Label(composite_15, SWT.NONE);
		lblAlertInactive.setAlignment(SWT.RIGHT);
		lblAlertInactive.setFont(AppMain.fontT);
		lblAlertInactive.setBounds(1045, 205, 40, 30);
		lblAlertInactive.setText(" 0");
		lblAlertInactive.setBackground(SWTResourceManager.getColor( 255,240,240));
		
		Label lblNewLabel_4 = new Label(composite_15, SWT.NONE);
		lblNewLabel_4.setBounds(0, 0, 1450, 480);
		lblNewLabel_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT)); 
		lblNewLabel_4.setBackgroundImage(slice_page1);
		
		lblApActive.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblApInactive.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblTagActive.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblTagInactive.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));

		Composite comp_dt = new Composite(composite_15, SWT.NONE);
		comp_dt.setLayout(new GridLayout(1, true));
		comp_dt.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		comp_dt.setBounds(1045, 45,400,60);
		/*
		 * Label lbldate_1 = new Label(comp_dt, SWT.NONE);
		 * lbldate_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		 * lbldate_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE));
		 * lbldate_1.setFont(font2); lbldate_1.setBounds(1020, 55, 50, 30);
		 * lbldate_1.setText("Date ");
		 */		


		lblDate = new Label(comp_dt, SWT.NONE);
		lblDate.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblDate.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE));
		lblDate.setFont(font2);
		lblDate.setLayoutData(new GridData(SWT.LEFT,SWT.CENTER,true, true));
		lblDate.setText("Date 2019-02-02");

		lblinterval = new Label(comp_dt, SWT.NONE);
		lblinterval.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblinterval.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE));
		lblinterval.setFont(font2);
		lblinterval.setLayoutData(new GridData(SWT.LEFT,SWT.CENTER,true, true));
		lblinterval.setText("Time Interval 10 sec ");

		comp_dt.moveAbove(lblNewLabel_4);
//		lblDate.moveAbove(lblNewLabel_4);
//		lblinterval.moveAbove(lblNewLabel_4);

		Composite composite_3 = new Composite(composite, SWT.NONE);
		GridLayout gl_composite_3 = new GridLayout(1, false);
		gl_composite_3.marginRight = 10;
		gl_composite_3.marginLeft = 10;
		composite_3.setLayout(gl_composite_3);
		composite_3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		composite_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		
		SashForm  sash1 = new SashForm(composite_3, SWT.HORIZONTAL);
//		sash1.setLayout(gl_composite_3);
		sash1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		sash1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		
		tableViewer_1 = new TableViewer(sash1, SWT.BORDER | SWT.FULL_SELECTION);
		
		table_1 = tableViewer_1.getTable();
		table_1.setHeaderVisible(true);
		table_1.setLinesVisible(true);
		table_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		table_1.setFont(AppMain.font11);
		table_1.setHeaderBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		tableViewer_1.setUseHashlookup(true);	
		
		TableViewerColumn tvc = new TableViewerColumn(tableViewer_1, SWT.CENTER);
		tvc.getColumn().setWidth(0);
		tvc.setLabelProvider(new ColumnLabelProvider() {} );

		
		tvc = new TableViewerColumn(tableViewer_1, SWT.NONE);
		tvc.setLabelProvider(new myColumnProvider() {
			public String getText(Object element) {
				return element == null ? "" :((MoteStatus)element).getDispNm()   ;
			}
		});
		TableColumn tblclmnNewColumn_6 = tvc.getColumn();
		tblclmnNewColumn_6.setAlignment(SWT.CENTER);
		tblclmnNewColumn_6.setWidth(60);
		tblclmnNewColumn_6.setText("MoteID");
		
		TableViewerColumn tableViewerColumn_7 = new TableViewerColumn(tableViewer_1, SWT.NONE);
		tableViewerColumn_7.setLabelProvider(new myColumnProvider() {
			public String getText(Object element) {
				// TODO Auto-generated method stub
				return element == null ? "" :((MoteStatus)element).getDesc() +""  ;
			}
		});
		TableColumn tblclmnNewColumn_7 = tableViewerColumn_7.getColumn();
		tblclmnNewColumn_7.setAlignment(SWT.LEFT);
		tblclmnNewColumn_7.setWidth(160);
		tblclmnNewColumn_7.setText("Description");
		
		TableViewerColumn tableViewerColumn_8 = new TableViewerColumn(tableViewer_1, SWT.NONE);
		tableViewerColumn_8.setLabelProvider(new myColumnProvider() {
			public String getText(Object element) {
				if (element == null) return "";
				switch (((MoteStatus)element).getAct()) {
				case 2:
					return "Active";

				case 1:
					return "Sleep";

				default:
					return "Inactive";
				}
			}

		});
		TableColumn tblclmnNewColumn_8 = tableViewerColumn_8.getColumn();
		tblclmnNewColumn_8.setAlignment(SWT.CENTER);
		tblclmnNewColumn_8.setWidth(80);
		tblclmnNewColumn_8.setText("Active");
		
		TableViewerColumn tableViewerColumn_9 = new TableViewerColumn(tableViewer_1, SWT.NONE);
		tableViewerColumn_9.setLabelProvider(new myColumnProvider() {
			public String getText(Object element) {
				double dbatt = ((MoteStatus)element).getBatt() ;
				return element == null ? "" : (dbatt == 0) ? "" : String.format("%1.3f", dbatt)  ;
			}
		});
		TableColumn tblclmnNewColumn_9 = tableViewerColumn_9.getColumn();
		tblclmnNewColumn_9.setAlignment(SWT.CENTER);
		tblclmnNewColumn_9.setWidth(90);
		tblclmnNewColumn_9.setText("Battery(V)");
		
		TableViewerColumn tvc_battdt = new TableViewerColumn(tableViewer_1, SWT.NONE);
		tvc_battdt.setLabelProvider(new myColumnProvider() {
			public String getText(Object element) {
				String lsBattDt = ((MoteStatus)element).getBattDt();
				return lsBattDt.length() < 8 ? "" : lsBattDt.substring(0, 4) + "-" + lsBattDt.substring(4, 6) + "-" + lsBattDt.substring(6) ;
			}
		});
		
		TableColumn tcl_battdt = tvc_battdt.getColumn();
		tcl_battdt.setAlignment(SWT.CENTER);
		tcl_battdt.setWidth(100);
		tcl_battdt.setText("Bat.설치일");
		
		TableViewerColumn tvc_day = new TableViewerColumn(tableViewer_1, SWT.NONE);
		tvc_day.setLabelProvider(new myColumnProvider() {
			public String getText(Object element) {
				String lval = "";
			    DateFormat dateFmt1 = new SimpleDateFormat("yyyyMMdd");
				String battdt = ((MoteStatus)element).getBattDt() ;
				if ( battdt.length() == 8 )
					try {
						Date startd = dateFmt1.parse( battdt );
						Date currentTime = new Date ();
						long lday = ( currentTime.getTime() - startd.getTime() ) / (24 * 60 * 60 * 1000);
						lval = String.format("%,d",lday);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				return lval ;

			}
		});

		TableColumn tcl_day = tvc_day.getColumn() ;
		tcl_day.setText("경과일수");
		tcl_day.setAlignment(SWT.CENTER);
		tcl_day.setWidth(70);

		tvc = new TableViewerColumn(tableViewer_1, SWT.NONE);
		tvc.setLabelProvider(new myColumnProvider() {
			public String getText(Object element) {
				if (element == null) return "" ;

				return AppMain.PLACE_NM[ ((MoteStatus)element).getPlace() ] ;
			}
		});

		TableColumn tcl_place = tvc.getColumn() ;
		tcl_place.setText("장소");
		tcl_place.setAlignment(SWT.CENTER);
		tcl_place.setWidth(60);

		tvc = new TableViewerColumn(tableViewer_1, SWT.NONE);
		tvc.setLabelProvider(new myColumnProvider() {
			public String getText(Object element) {
				if (element == null) return "" ;
				return AppMain.locname[ ((MoteStatus)element).getLoc() ];
			}
		});

		TableColumn tcl_loc = tvc.getColumn() ;
		tcl_loc.setText("위치");
		tcl_loc.setAlignment(SWT.CENTER);
		tcl_loc.setWidth(80);

		tvc = new TableViewerColumn(tableViewer_1, SWT.NONE);
		tvc.setLabelProvider(new myColumnProvider() {
			public String getText(Object e) {
				if (e == null) return "" ;
				String str = "" ;

				if ( ((MoteStatus)e).getObcnt() > 0 ) str = "O/B " ;
				if (((MoteStatus)e).getBatt() < 3.5 && ((MoteStatus)e).getAct() == 2 ) str += "L/B" ;
				
				return  str.trim().replace(" ", ",");
			}
		});

		TableColumn tclst = tvc.getColumn() ;
		tclst.setText("상태");
		tclst.setAlignment(SWT.CENTER);
		tclst.setWidth(80);
		
		table_1.redraw();
//		Composite composite_1 = new Composite(composite_3, SWT.V_SCROLL);
//		composite_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
//		composite_1.setSize(438, 0);
//		composite_1.setLayout(new GridLayout(1, false));
//		composite_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		
		tableViewer = new TableViewer(sash1, SWT.BORDER | SWT.FULL_SELECTION );
		
		table = tableViewer.getTable();
		table.setHeaderBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setFont(AppMain.font11);
		tableViewer.setUseHashlookup(true);

		TableViewerColumn tvc2 = new TableViewerColumn(tableViewer, SWT.NONE);
		tvc2.setLabelProvider(new ColumnLabelProvider() {});

		tvc2 = new TableViewerColumn(tableViewer, SWT.NONE);
		tvc2.setLabelProvider(new myColumnProvider2() {
			public String getText(Object element) {
				// TODO Auto-generated method stub
				//return element == null ? "" : element.toString();
				return element == null ? "" : dateFormat.format( new Date( ((MoteInfo)element).getTm().getTime() ) ) ;
			}
		});
		TableColumn tblclmnTime = tvc2.getColumn();
		tblclmnTime.setAlignment(SWT.CENTER);
		tblclmnTime.setWidth(180);
		tblclmnTime.setText("Time");

		tvc2 = new TableViewerColumn(tableViewer, SWT.NONE);
		tvc2.setLabelProvider(new myColumnProvider2() {
			public String getText(Object element) {
				return element == null ? "" : ((MoteInfo)element).getMoteStatus().getDesc()  ;
			}
		});
		TableColumn tc1 = tvc2.getColumn();
		tc1.setAlignment(SWT.CENTER);
		tc1.setWidth(60);
		tc1.setText("ID");
		
		tvc2 = new TableViewerColumn(tableViewer, SWT.NONE);
		tvc2.setLabelProvider(new myColumnProvider2() {
			public String getText(Object element) {
				if (element == null)  return "";
				double temp = (AppMain.MOTECNF.getAdgb().equals("0") ? ((MoteInfo)element).getTemp2() : ((MoteInfo)element).getTemp()) ;
				return String.format("%,2.2f", temp ) ;
			}
		});
		TableColumn tblclmnNewColumn_3 = tvc2.getColumn();
		tblclmnNewColumn_3.setAlignment(SWT.CENTER);
		tblclmnNewColumn_3.setWidth(80);
		tblclmnNewColumn_3.setText("온도");

		tvc2 = new TableViewerColumn(tableViewer, SWT.NONE);
		tvc2.setLabelProvider(new myColumnProvider2() {
			public String getText(Object element) {
				if (element == null)  return "";
				double humi = (AppMain.MOTECNF.getAdgb().equals("0") ? ((MoteInfo)element).getHumi2() : ((MoteInfo)element).getHumi()) ;
				return String.format("%,2.2f", humi ) ;
			}
		});
		TableColumn tc_humi = tvc2.getColumn();
		tc_humi.setAlignment(SWT.CENTER);
		tc_humi.setWidth(80);
		tc_humi.setText("습도");

		tvc2 = new TableViewerColumn(tableViewer, SWT.NONE);
		tvc2.setLabelProvider(new myColumnProvider2() {
			public String getText(Object element) {
				return element == null ? "" : ((MoteInfo)element).getMold()  ;
			}
		});
		TableColumn tcmold = tvc2.getColumn();
		tcmold.setAlignment(SWT.CENTER);
		tcmold.setWidth(80);
		tcmold.setText("Mold#");

		tvc2 = new TableViewerColumn(tableViewer, SWT.NONE);
		tvc2.setLabelProvider(new myColumnProvider2() {
			public String getText(Object element) {
				return element == null ? "" : AppMain.PLACE_NM[ ((MoteInfo)element).getPlace() ] ;
			}
		});
		TableColumn tcplace = tvc2.getColumn();
		tcplace.setAlignment(SWT.CENTER);
		tcplace.setWidth(80);
		tcplace.setText("장소");

		tvc2 = new TableViewerColumn(tableViewer, SWT.NONE);
		tvc2.setLabelProvider(new myColumnProvider2() {
			public String getText(Object element) {
				return element == null ? "" : AppMain.locname[ ((MoteInfo)element).getLoc() ] ;
			}
		});
		TableColumn tcloc = tvc2.getColumn();
		tcloc.setAlignment(SWT.CENTER);
		tcloc.setWidth(80);
		tcloc.setText("위치");

		
		tableViewer.setContentProvider(new ContentProvider());
		tableViewer_1.setContentProvider(new ContentProvider_1());

		sash1.setWeights(new int[] {55,45});
		
		uiUpdateThread = new MyThread(Display.getCurrent(), AppMain.MOTECNF.getMeasure() * 1000) ;
		uiUpdateThread.start();
	}

	private class MyThread extends Thread {
		private Display display = null;
		private int interval ;
		MyThread(Display display, int interval){
			this.display = display ;
			this.interval = interval ;
		}
		@Override
		public void run() {
			while(!Thread.currentThread().isInterrupted() && !lblinterval.isDisposed()) {
				display.syncExec(new Runnable() {
					@Override
					public void run() {
						refreshSensorList();
					}
				});
				try {
					Thread.sleep(interval);
				} catch (InterruptedException e) {
//					e.printStackTrace();
				}

			}
		}

	}

	int activeCnt = 0;
	int inactiveCnt = 0;

	int activeSsCnt = 0;
	int failCnt = 0;
	int moteLBCnt = 0;
	int oBCnt = 0;
	private Table table_1;
	private Timestamp time_c = Timestamp.valueOf("1900-01-01 00:00:00") ;

//	@SuppressWarnings("unchecked")
	public void refreshSensorList() {
	    EntityManager em = AppMain.emf.createEntityManager();
		em.clear();
		em.getEntityManagerFactory().getCache().evictAll();
		
//		AppMain.MOTECNF = em.createQuery("select m from MoteConfig m ", MoteConfig.class).getSingleResult() ;
		AppMain.MOTECNF = em.find( MoteConfig.class,(short)1) ;
		ArrayList<MoteInfo> tempList = new ArrayList<MoteInfo>();
		ArrayList<MoteStatus> apList = new ArrayList<MoteStatus>();
        
        TypedQuery<MoteStatus> q2 = 
        		 em.createQuery("select t from MoteStatus t where t.spare = 'N' order by t.seq", MoteStatus.class);
        q2.getResultList().stream().forEach(t -> apList.add(t) );

		activeCnt = 0;
		inactiveCnt = 0;
		activeSsCnt = 0;
		failCnt = 0;
		moteLBCnt = 0;
		oBCnt = 0;
/*
		for (MoteStatus moteStatus : apList) {
			if(moteStatus.getAct() == 2) {
				activeCnt++;
			}else {
				inactiveCnt++;
			}
			if (moteStatus.getBatt() <= (3.6 * 0.3) && moteStatus.getAct() > 0 ) moteLBCnt++ ;
		}
*/
		activeCnt = (int)apList.stream().filter(a -> a.getAct() == 2).count() ;
		inactiveCnt = (int)apList.stream().filter(a -> a.getAct() != 2).count() ;
		moteLBCnt = (int)apList.stream().filter(a -> a.getBatt() < 3.5 && a.getAct() == 2).count() ;
		oBCnt = (int)apList.stream().filter(a -> a.getGubun().equals("S") && a.getObcnt() > 0).count() ;
		
		
/*		
        tagCount.clear();

		for (MoteStatus moteStatus : apList) {
			int count = 0;
			for (MoteInfo temp1 : tempList) {
				if(moteStatus.getSensorNo() == temp1.getSensorNo() ) {
					count++;
				}
			}
			if(count > 0) {
				tagCount.put(moteStatus.getSensorNo(), count);
			}
		}
*/

		time_c = em.createNamedQuery("LasTime.findone", Timestamp.class)
				.setParameter("id", AppMain.PLACE).getSingleResult() ;

//		List<Timestamp> timel= em.createQuery("select t.lastm from LasTime t ").getResultList() ;
/*		
        TypedQuery<MoteInfo> qMaxTime = em.createQuery("select t from MoteInfo t order by t.time desc ", MoteInfo.class);
        qMaxTime.setFirstResult(0);
        qMaxTime.setMaxResults(1);
        final MoteInfo tag = (MoteInfo) qMaxTime.getSingleResult();
*/        
//		time_c = (timel.get(0).compareTo(timel.get(1)) >= 0 ? timel.get(0) : timel.get(1) );
        TypedQuery<MoteInfo> qMotes = em.createQuery("select t from MoteInfo t "
        		+ "where t.tm = :times  order by t.seq  "
        		, MoteInfo.class);

        qMotes.setParameter("times", time_c).getResultList().stream().forEach( t -> tempList.add(t));
        
        activeSsCnt = (int)apList.stream().filter(a -> "S".equals(a.getGubun()) && a.getAct() == 2 ).count() ;
        failCnt = (int)apList.stream().filter(a -> "S".equals(a.getGubun()) && a.getAct() != 2 ).count() ;
//		for (MoteInfo moteInfo : tempList) {
//			switch (moteInfo.getAct()) {
//			case 0:
//				failCnt++;
//				break ;
//			default:
//				activeSsCnt++;
//				break ;
//			}
//		}

		em.close();


//		if(!canvas.isDisposed()) {
			
				//String apStatus = "활성 : " + activeCnt + " | 비활성 : " + inactiveCnt;
				//String tagStatus = "활성 : " + activeTagCnt + " | SOS : " + sosTagCnt;
				lblinterval.setText("Time Interval  "+ AppMain.MOTECNF.getMeasure() + " sec ");
				lblDate.setText( "Date  " + dateFormat.format(time_c )) ;
				lblDate.requestLayout();
				
				lblApActive.setText(activeCnt+"");
				lblApInactive.setText(inactiveCnt+"");

				lblTagActive.setText(activeSsCnt+"");
				lblTagInactive.setText(failCnt+"");
				lblAlertActive.setText(moteLBCnt+"");
				lblAlertInactive.setText(oBCnt+"");

				tableViewer_1.setInput(apList);
				tableViewer_1.refresh();
				tableViewer.setInput(tempList);
				tableViewer.refresh();
			
//			sync.syncExec(()->{
//				canvas.redraw();
////				pushSession.stop();
//			});

//		}

	}

	class myColumnProvider extends ColumnLabelProvider {
		@Override
		public Color getForeground(Object e) {
			Color col = SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE) ;
			if (e == null) return col ;
			if ( ((MoteStatus)e).getObcnt() > 0 ) col = AppMain.colout ;
			else if (((MoteStatus)e).getBatt() < 3.5 && ((MoteStatus)e).getAct() == 2 ) col = AppMain.collow ;
			return col ;
		}
	}
	class myColumnProvider2 extends ColumnLabelProvider {
		@Override
		public Color getForeground(Object e) {
			Color col = SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE) ;
			if (e == null) return col ;
			double humi = (AppMain.MOTECNF.getAdgb().equals("0") ? ((MoteInfo)e).getHumi2() : ((MoteInfo)e).getHumi()) ;
			double temp = (AppMain.MOTECNF.getAdgb().equals("0") ? ((MoteInfo)e).getTemp2() : ((MoteInfo)e).getTemp()) ;

			if ( ((MoteInfo)e).getAct() == 2 && 
					( AppMain.MOTECNF.getMaxhumi() < humi 
					|| AppMain.MOTECNF.getMinhumi() > humi 
					|| AppMain.MOTECNF.getMaxtemp() < temp 
					|| AppMain.MOTECNF.getMintemp() > temp 
				) ) col = AppMain.colout ;

			return col ;
		}
	}
	
}