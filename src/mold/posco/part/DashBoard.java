package mold.posco.part;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import mold.posco.model.FindMoteInfo;
import mold.posco.model.MoteConfig;
import mold.posco.model.MoteStatus;
import mold.posco.model.Vstatus;

@SuppressWarnings("unchecked")
public class DashBoard {

//	List<MoteInfo> selectedTagList = new ArrayList<MoteInfo>();
	Point selectedPoint = new Point(0, 0);
	EntityManager em = AppMain.emf.createEntityManager();
	HashMap<Integer, Integer> tagCount = new HashMap<Integer, Integer>();
	Bundle bundle = FrameworkUtil.getBundle(this.getClass());
    FindMoteInfo findMoteinfo = new FindMoteInfo() ;
	List<MoteStatus> sList  ;
	SensorWidget2[] sWidget = new SensorWidget2[8]; // 공장, 수리장

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
    
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    DateFormat dateFmt2 = new SimpleDateFormat("HH:mm:ss");
    Font font1 = SWTResourceManager.getFont("Microsoft Sans Serif", 22 , SWT.NORMAL ) ;
    Font font2 = SWTResourceManager.getFont("Calibri", 14, SWT.NORMAL);
    Font font3 = SWTResourceManager.getFont("Consolas", 11, SWT.NORMAL ) ;
    Thread uiUpdateThread ;
	Composite composite_3;
    
	public DashBoard(Composite parent, int style) {

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
		lblApActive.setFont(font1);
		lblApActive.setBounds(295, 170, 40, 30);
		lblApActive.setText("99");
		
		lblApInactive = new Label(composite_15, SWT.NONE);
		lblApInactive.setAlignment(SWT.RIGHT);
		lblApInactive.setFont(font1);
		lblApInactive.setBounds(295, 205, 40, 30);
		lblApInactive.setText("99");
		
		lblTagActive = new Label(composite_15, SWT.NONE);
		lblTagActive.setAlignment(SWT.RIGHT);
		lblTagActive.setFont(font1);
		lblTagActive.setBounds(665, 170, 40, 30);
		lblTagActive.setText("99");
		
		lblTagInactive = new Label(composite_15, SWT.NONE);
		lblTagInactive.setAlignment(SWT.RIGHT);
		lblTagInactive.setFont(font1);
		lblTagInactive.setBounds(665, 205, 40, 30);
		lblTagInactive.setText(" 0");
		
		lblAlertActive = new Label(composite_15, SWT.NONE);
		lblAlertActive.setAlignment(SWT.RIGHT);
		lblAlertActive.setFont(font1);
		lblAlertActive.setBounds(1045, 170, 40, 30);
		lblAlertActive.setText(" 0");
		lblAlertActive.setBackground(SWTResourceManager.getColor( 255,240,240));
		
		lblAlertInactive = new Label(composite_15, SWT.NONE);
		lblAlertInactive.setAlignment(SWT.RIGHT);
		lblAlertInactive.setFont(font1);
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
		lblinterval.setText("Time Interval 10 sec  ");
//		lblinterval.pack();
//		lblinterval.setLocation(0,30);

		comp_dt.moveAbove(lblNewLabel_4);

//		lblDate.moveAbove(lblNewLabel_4);
//		lblinterval.moveAbove(lblNewLabel_4);

		composite_3 = new Composite(composite, SWT.NONE);
		GridLayout gl_composite_3 = new GridLayout(1, false);
		gl_composite_3.marginRight = 40;
		gl_composite_3.marginLeft = 40;
		composite_3.setLayout(gl_composite_3);
		composite_3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		composite_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Label lblt = new Label(composite_3, SWT.NONE) ;
		lblt.setText(" 2 연주공장 / 1머신");
		lblt.setFont(SWTResourceManager.getFont("맑은 고딕", 20, SWT.BOLD )); 
		lblt.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, true));
		lblt.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblt.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE));
		lblt.pack();
		
		Composite group1 = new Composite(composite_3, SWT.NONE);

//		group1.setFont(SWTResourceManager.getFont( "Microsoft Sans Serif", 20, SWT.BOLD));
		group1.setLayout(new GridLayout(1,true));
//		group1.setText("연주 공장");
		group1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		group1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));

		Composite child = new Composite(group1, SWT.NONE);
		GridLayout gl_child = new GridLayout(4,true) ;
		gl_child.horizontalSpacing = 50 ;
		gl_child.verticalSpacing = 10 ;
		gl_child.marginLeft = 30 ;
		gl_child.marginRight = 30 ;
		child.setLayout(gl_child);
		child.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		child.setBackgroundMode(SWT.INHERIT_FORCE);
		child.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		EntityManager em = AppMain.emf.createEntityManager();

		sList = em.createNamedQuery("MoteStatus.sensorList", MoteStatus.class).getResultList() ;
		
		em.close();
		
		for (int i = 0 ;i < sWidget.length ; i++) {
			sWidget[i] = new SensorWidget2(child, sList.get(i));
		}

		Composite comp_l = new Composite(composite_3, SWT.NONE) ;
		GridLayout gl_line = new GridLayout(2,true) ;
		
		gl_line.horizontalSpacing = 140 ;
		gl_line.marginLeft = 30 ;
		gl_line.marginRight = 30 ;
		comp_l.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		comp_l.setLayout(gl_line);
		Image img_line = SWTResourceManager.getImage( "images/line01.png");
		lbl_l1 = new Label(comp_l,SWT.NONE);
		lbl_l1.setImage(img_line);
		lbl_l2 = new Label(comp_l,SWT.NONE);
		lbl_l2.setImage(img_line);
		comp_l.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lbl_l1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lbl_l2.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lbl_l1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		lbl_l2.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		{
			Label lbl = new Label (comp_l,SWT.NONE);
			lbl.setText("Stand 1");
			lbl.setFont(AppMain.font2b);
			lbl.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE));
			lbl.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
			lbl.setLayoutData(new GridData(SWT.CENTER, SWT.UP, true, true));
			lbl = new Label (comp_l,SWT.NONE);
			lbl.setText("Stand 2");
			lbl.setFont(AppMain.font2b);
			lbl.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE));
			lbl.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
			lbl.setLayoutData(new GridData(SWT.CENTER, SWT.UP, true, true));
		}
		Image category = SWTResourceManager.getImage( "images/category.png");
		Label lbl = new Label(composite_3, SWT.NONE) ;
		lbl.setImage(category);
		lbl.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, true, true));

		uiUpdateThread = new MyThread(Display.getCurrent(), AppMain.MOTECNF.getMeasure() * 1000 ) ;
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
						composite_3.layout();
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
	private Timestamp time_c = Timestamp.valueOf("1900-01-01 00:00:00") ;
	Label lbl_l1, lbl_l2 ;
//	@SuppressWarnings("unchecked")
	public void refreshSensorList() {
	    EntityManager em = AppMain.emf.createEntityManager();
		em.clear();
		em.getEntityManagerFactory().getCache().evictAll();
		
		activeCnt = 0;
		inactiveCnt = 0;
		activeSsCnt = 0;
		failCnt = 0;
		moteLBCnt = 0;
		oBCnt = 0;
		AppMain.VSTATUS = em.createNamedQuery("Vstatus.find", Vstatus.class).getSingleResult() ;
		AppMain.MOTECNF = em.find( MoteConfig.class,(short)1) ;
		
		time_c = findMoteinfo.getLasTime();
		
		activeCnt = AppMain.VSTATUS.getActcnt() ;
		inactiveCnt = AppMain.VSTATUS.getInactcnt() ;
		moteLBCnt = AppMain.VSTATUS.getLbcnt() ;

        activeSsCnt = AppMain.VSTATUS.getSactcnt();
        failCnt = AppMain.VSTATUS.getSinactcnt() ;
        oBCnt  = AppMain.VSTATUS.getObcnt() ;

		lblinterval.setText("Time Interval  "+ AppMain.MOTECNF.getMeasure() + " sec ");
		lblinterval.setVisible(false);
		
		lblDate.setText( "Date  " + dateFormat.format(time_c ) );
		lblDate.requestLayout();
		lblApActive.setText(activeCnt+"");
		lblApInactive.setText(inactiveCnt+"");

		lblTagActive.setText(activeSsCnt+"");
		lblTagInactive.setText(failCnt+"");
		lblAlertActive.setText(moteLBCnt+"");
		lblAlertInactive.setText(oBCnt+"");

		sList = null;
		sList = em.createNamedQuery("MoteStatus.sensorList", MoteStatus.class).getResultList() ;
		em.close();

		MoteStatus mote =  sList.stream().filter(a ->  a.getLoc() == 1 && a.getStand() == 1).findAny().orElse(new MoteStatus()) ;
		sWidget[0].setSensor(  mote ) ;
		mote = sList.stream().filter(a ->  a.getLoc() == 3 && a.getStand() == 1).findAny().orElse(new MoteStatus()) ;
		sWidget[1].setSensor( mote ) ;
		mote =  sList.stream().filter(a ->  a.getLoc() == 1 && a.getStand() == 2).findAny().orElse(new MoteStatus()) ;
		sWidget[2].setSensor( mote ) ;
		mote =  sList.stream().filter(a ->  a.getLoc() == 3 && a.getStand() == 2).findAny().orElse(new MoteStatus()) ;
		sWidget[3].setSensor( mote ) ;
		mote =  sList.stream().filter(a ->  a.getLoc() == 2 && a.getStand() == 1).findAny().orElse(new MoteStatus()) ;
		sWidget[4].setSensor( mote ) ;
		mote =  sList.stream().filter(a ->  a.getLoc() == 4 && a.getStand() == 1).findAny().orElse(new MoteStatus()) ;
		sWidget[5].setSensor( mote ) ;
		mote =  sList.stream().filter(a ->  a.getLoc() == 2 && a.getStand() == 2).findAny().orElse(new MoteStatus()) ;
		sWidget[6].setSensor( mote ) ;
		mote =  sList.stream().filter(a ->  a.getLoc() == 4 && a.getStand() == 2).findAny().orElse(new MoteStatus()) ;
		sWidget[7].setSensor( mote ) ;
		
		for ( int i = 0 ;i < sWidget.length ; i++) {
			sWidget[i].setValues((short)0);
		}
		Image img_line = AppMain.resize( new Image( null, "images/line01.png"), lbl_l1.getSize().x,lbl_l1.getSize().y) ;
		lbl_l1.setImage(img_line);
		lbl_l2.setImage(img_line);

		lblinterval.setVisible(true);

		
	}
	
}