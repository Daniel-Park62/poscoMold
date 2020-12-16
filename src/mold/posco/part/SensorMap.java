package mold.posco.part;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import mold.posco.model.MoteInfo;
import mold.posco.model.MoteStatus;
import mold.posco.model.MoteConfig;

public class SensorMap  {

	Thread uiUpdateThread;
	public SensorMap(Composite parent, int style) {
		// TODO Auto-generated constructor stub
		postConstruct(parent);
	}
	//	EntityManager em = e4Application.emf.createEntityManager();

	List<MoteInfo> moteInfoList ;
	List<MoteStatus> sList , rList, rList2 ;
	List<MoteStatus> rListF = new ArrayList<MoteStatus>(),  rListR = new ArrayList<MoteStatus>() ;
	Composite comp_r, comp_r2 ;
	
	SensorWidget2[] sWidget, sWidget2 ; // 공장, 수리장
	RepeatWidget[] rWidget , rWidgetF, rWidgetR ; // 고정형, 이동형(공장), 이동형(수리장)
	float levelScale = 1 ;
	Canvas canvas;

	Image image_active ;
	Image image_inactive ;
	Image image_lowbattery ;
	Image image_sos ;
	Image image_popup_inactive_1;

	Label lblApActive;
	Label lblApInactive;
	Label lblApLow;

	Label lblTagActive;
	Label lblTagInactive;
	Label lblTagLow;
	Label lblDate , lblTime ;
//	Composite child ;

	DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
	ResourceManager resourceManager = new LocalResourceManager(JFaceResources.getResources());

	@PostConstruct
	public void postConstruct(Composite parent) {

		//		parent.setLayout(new FillLayout(SWT.HORIZONTAL));
		parent.setLayout(new GridLayout(1, false));
		Image image = SWTResourceManager.getImage("images/map.png") ;
		image_active = SWTResourceManager.getImage("images/icon_active.png") ;
		image_inactive = SWTResourceManager.getImage("images/icon_inactive.png") ;
		image_lowbattery = SWTResourceManager.getImage("images/icon_lowbattery.png") ;
		image_sos = SWTResourceManager.getImage("images/moteicon_sos.png") ;
		image_popup_inactive_1 = SWTResourceManager.getImage("images/popup_inactive_1.png");
		Image slice_page2_1 = SWTResourceManager.getImage( "images/slice_page2_1.png") ;
		Image slice_page2_2 = SWTResourceManager.getImage( "images/slice_page2_2.png") ;
		Image category = SWTResourceManager.getImage( "images/category.png") ;

		//		Composite composite = new Composite(parent, SWT.NONE);
		//		composite.setLayout(new GridLayout(1, false));

		Composite composite_1 = new Composite(parent, SWT.NONE);
		composite_1.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				uiUpdateThread.interrupt();
			}
		});
		GridData gd_composite_1 = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		//		gd_composite_1.minimumHeight = 240;
		gd_composite_1.heightHint = 200;
		//		gd_composite_1.widthHint = 2500;
		//		gd_composite_1.minimumWidth = 1920;
		composite_1.setLayoutData(gd_composite_1);
//		composite_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
//		composite_1.setBackgroundMode(SWT.INHERIT_FORCE);

		lblDate = new Label(composite_1, SWT.NONE);

		lblDate.setBounds(340, 60, 60, 30);
		lblDate.setFont(SWTResourceManager.getFont( "Microsoft Sans Serif", 16, SWT.NORMAL));
		lblDate.setText("New Label");
		lblDate.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblDate.setBackground(new Color (Display.getCurrent(), 141,153,208));

		lblTime = new Label(composite_1, SWT.NONE);
		lblTime.setBounds(340, 90 , 60, 30);
		lblTime.setFont(SWTResourceManager.getFont( "Microsoft Sans Serif", 22, SWT.NORMAL));
		lblTime.setText("New Label");
		lblTime.setBackground(SWTResourceManager.getColor( 141,153,208));
		lblTime.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

		Label lblt = new Label(composite_1, SWT.NONE);
		lblt.setBounds(0, 0, 1900, 200);
//		lblt.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblt.setImage(slice_page2_1);

		//MAP

		Composite comp_map = new Composite(parent, SWT.NONE);
		GridLayout glm1 = new GridLayout(2,true) ;
		glm1.marginWidth = 10 ;
		glm1.verticalSpacing = 50 ;
		comp_map.setLayout(glm1);
		comp_map.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		comp_map.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

		Composite group0 = new Composite(comp_map, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(8).equalWidth(true).applyTo(group0);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).span(2, 1).applyTo(group0);
		
		Group group1 = new Group(comp_map, SWT.NONE);
		Group group2 = new Group(comp_map, SWT.NONE);
		group1.setFont(SWTResourceManager.getFont( "Microsoft Sans Serif", 20, SWT.BOLD));
		group2.setFont(SWTResourceManager.getFont( "Microsoft Sans Serif", 20, SWT.BOLD));
		group1.setLayout(new GridLayout(1,true));
		group2.setLayout(new GridLayout(1,true));
		group1.setText("연주 공장");
		group2.setText("연주 수리장");
		group1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		group2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		group1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
//		group2.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));

		Composite r_chr = new Composite(group1, SWT.NONE);
		Composite r_chr2 = new Composite(group2, SWT.NONE);
		GridLayout gl_r1 = new GridLayout(4,true) ;
		gl_r1.horizontalSpacing = 20 ;
		gl_r1.verticalSpacing = 10 ;
		r_chr.setLayout(gl_r1);
		r_chr2.setLayout(gl_r1);
		r_chr.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		r_chr.setBackgroundMode(SWT.INHERIT_FORCE);
		r_chr.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		r_chr2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		r_chr2.setBackgroundMode(SWT.INHERIT_FORCE);
		r_chr2.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		
		Composite child = new Composite(group1, SWT.NONE);
		GridLayout gl_child = new GridLayout(4,true) ;
		gl_child.horizontalSpacing = 25 ;
		gl_child.verticalSpacing = 15 ;
		child.setLayout(gl_child);
		child.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		child.setBackgroundMode(SWT.INHERIT_FORCE);
		child.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));

		EntityManager em = AppMain.emf.createEntityManager();

		sList = (List<MoteStatus>) em.createNamedQuery("MoteStatus.sensorList", MoteStatus.class).getResultStream()
				.sorted(Comparator.comparing(MoteStatus::getSeq )).collect(Collectors.toList()) ;
		rList = em.createNamedQuery("MoteStatus.rList", MoteStatus.class).getResultList() ;
		rList2 = em.createNamedQuery("MoteStatus.rList2", MoteStatus.class).getResultList() ;
		
		em.close();

		rWidget = new RepeatWidget[rList.size() ] ;
		rList2.stream().forEach(a -> { if (a.getPlace() == 0 )  rListF.add(a); else rListR.add(a); } );
		
		for (int i = 0 ;i < rWidget.length ; i++) {
			rWidget[i] = new RepeatWidget(r_chr, "R", 0, 0);
			rWidget[i].setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		}

		
		sWidget = new SensorWidget2[sList.size()];
		
		for (int i = 0 ;i < sList.size() ; i++) {
			sWidget[i] = new SensorWidget2(group0, sList.get(i));
		}

		Composite child2 = new Composite(group2, SWT.NONE);
		child2.setLayout(gl_child);
		child2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		child2.setBackgroundMode(SWT.INHERIT_FORCE);
		child2.setBackground(group2.getBackground() );
		Label ldummy = new Label(child2, SWT.NONE);
		ldummy.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 4, 1));
		ldummy.setBackground(child2.getBackground());
		
//		sWidget2 = new SensorWidget2[8];
//		
//		for (int i = 0 ;i < 8 ; i++) {
//			sWidget2[i] = new SensorWidget2(child2, sList.get(i));
//		}

		comp_r = new Composite(group1, SWT.NONE);
		GridLayout glmr = new GridLayout(7,true) ;
		gl_child.horizontalSpacing = 20 ;
		gl_child.verticalSpacing = 20 ;
		comp_r.setLayout(glmr);
		comp_r.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		comp_r.setBackgroundMode(SWT.INHERIT_FORCE);
		comp_r.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));


		comp_r2 = new Composite(group2, SWT.NONE);
		comp_r2.setLayout(glmr);
		comp_r2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		comp_r2.setBackgroundMode(SWT.INHERIT_FORCE);
		comp_r2.setBackground(group2.getBackground());

		rWidgetF = new RepeatWidget[ rListF.size() ] ;
		
		for (int i = 0 ;i < rWidgetF.length ; i++) {
			rWidgetF[i] = new RepeatWidget(comp_r, "R", 1, 0);
			rWidgetF[i].setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		}

		rWidgetR = new RepeatWidget[ rListR.size() ] ;
		
		for (int i = 0 ;i < rWidgetR.length ; i++) {
			rWidgetR[i] = new RepeatWidget(comp_r2, "R", 1, 0);
			rWidgetR[i].setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		}

		{
			ImageData imageData = image.getImageData();
			Label lbl = new Label(child, SWT.NONE);
			lbl.setBounds(50, 0, imageData.width, imageData.height);
			lbl.setSize(imageData.width, imageData.height);
			lbl.setBackgroundImage(image);
		}

//		comp_map.setContent(child);
		
		//		reLocate();

		// 	map end

		Composite composite_2 = new Composite(parent, SWT.NONE);
		GridData gd_composite_2 = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_composite_2.widthHint = 1200;
		gd_composite_2.heightHint = 100;
		composite_2.setLayoutData(gd_composite_2);
		composite_2.setLayout(new GridLayout(2, false));
		composite_2.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TRANSPARENT));

		Composite composite_5 = new Composite(composite_2, SWT.NONE);
		GridData gd_composite_5 = new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1);
		gd_composite_5.minimumWidth = 900;
		gd_composite_5.heightHint = 100;
		gd_composite_5.widthHint = 850;
		composite_5.setLayoutData(gd_composite_5);
		composite_5.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		composite_5.setBackgroundImage(slice_page2_2);
//
//		Label lblBack = new Label(composite_5, SWT.NONE);
//		lblBack.setCursor(new Cursor(Display.getCurrent(),SWT.CURSOR_HAND));
//		lblBack.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseDown(MouseEvent e) {
//			}
//		});
//		lblBack.setBounds(41, 20, 69, 60);
//		lblBack.setText("");
//		lblBack.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TRANSPARENT));
		Font fonth = SWTResourceManager.getFont("맑은 고딕", 24, SWT.BOLD) ;
		lblApActive = new Label(composite_5, SWT.NONE);
		lblApActive.setFont(fonth);
		lblApActive.setAlignment(SWT.CENTER);
		lblApActive.setBounds(200, 15, 41, 61);
		lblApActive.setText(" 8");
		lblApActive.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TRANSPARENT));

		lblApInactive = new Label(composite_5, SWT.NONE);
		lblApInactive.setFont(fonth);
		lblApInactive.setAlignment(SWT.CENTER);
		lblApInactive.setBounds(290, 15, 41, 61);
		lblApInactive.setText(" 8");
		lblApInactive.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TRANSPARENT));

		lblApLow = new Label(composite_5, SWT.NONE);
		lblApLow.setFont(fonth);
		lblApLow.setAlignment(SWT.CENTER);
		lblApLow.setBounds(385, 15, 41, 61);
		lblApLow.setText(" 8");
		lblApLow.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TRANSPARENT));

		lblTagActive = new Label(composite_5, SWT.NONE);
		lblTagActive.setFont(fonth);
		lblTagActive.setAlignment(SWT.CENTER);
		lblTagActive.setBounds(580, 15, 41, 61);
		lblTagActive.setText(" 8");
		lblTagActive.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TRANSPARENT));

		lblTagInactive = new Label(composite_5, SWT.NONE);
		lblTagInactive.setFont(fonth);
		lblTagInactive.setAlignment(SWT.CENTER);
		lblTagInactive.setBounds(660, 15, 41, 61);
		lblTagInactive.setText(" 8");
		lblTagInactive.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TRANSPARENT));

		lblTagLow = new Label(composite_5, SWT.NONE);
		lblTagLow.setFont(fonth);
		lblTagLow.setAlignment(SWT.CENTER);
		lblTagLow.setBounds(765, 15, 41, 61);
		lblTagLow.setText(" 8");
		lblTagLow.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TRANSPARENT));

		{
			Label lbltc = new Label(composite_2, SWT.NONE );
			GridData gdc = new GridData( GridData.CENTER,GridData.FILL_VERTICAL,true,true );
			gdc.heightHint = 100 ;
			//			gdc.widthHint  = 700 ;
			lbltc.setLayoutData(gdc);
			lbltc.setImage(category);
			lbltc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TRANSPARENT));

		}

		uiUpdateThread = new MyThread(Display.getCurrent(), 10000) ;
		uiUpdateThread.start();

	} // postconstruct


	private class MyThread extends Thread {
		private Display display = null;
		private int interval ;
		MyThread(Display display, int interval){
			this.display = display ;
			this.interval = interval ;
		}
		@Override
		public void run() {
			while(!Thread.currentThread().isInterrupted() && !lblDate.isDisposed() ) {
				display.syncExec(new Runnable() {
					@Override
					public void run() {
						refreshConfig();
						if (!lblDate.isDisposed()) reLocate();
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

	private Timestamp time_c = Timestamp.valueOf("1900-01-01 00:00:00") ;
	int tagActcnt = 0, tagInActcnt = 0, tagLowcnt = 0;
	int moteActcnt = 0, moteInActcnt = 0, moteLowcnt = 0;

	private  void refreshConfig( ) {
		EntityManager em = AppMain.emf.createEntityManager();
		em.clear();
		AppMain.MOTECNF = em.find( MoteConfig.class,(short)1) ;

		//		time_c = em.createQuery("select max(t.tm) from MoteInfo t", Timestamp.class).getSingleResult() ;
		time_c = em.createNamedQuery("LasTime.findone", Timestamp.class)
				.setParameter("id", AppMain.PLACE).getSingleResult() ;

		TypedQuery<MoteInfo> qMotes = em.createQuery("select t from MoteInfo t "
				+ " where t.tm = :tm  order by t.act desc ", MoteInfo.class);
		qMotes.setParameter("tm", time_c );
		moteInfoList = qMotes.getResultList();
		em.close();
	}

	private   void reLocate() {
		EntityManager em = AppMain.emf.createEntityManager();
		em.getEntityManagerFactory().getCache().evictAll();
		lblDate.setText(dateFormat.format(new Date( time_c.getTime() ) ));
		lblTime.setText((timeFormat.format(new Date( time_c.getTime() ) ))  );
		lblDate.pack();
		lblTime.pack();
		sList = null;
//		sWidget = null;
		sList = em.createNamedQuery("MoteStatus.sensorList", MoteStatus.class).getResultList() ;
		rList = em.createNamedQuery("MoteStatus.rList", MoteStatus.class).getResultList() ;
		rList2 = em.createNamedQuery("MoteStatus.rList2", MoteStatus.class).getResultList() ;
		rListF.clear();
		rListR.clear();
		
		em.close();
		tagActcnt =  tagInActcnt =  tagLowcnt = 0;
		moteActcnt =  moteInActcnt =  moteLowcnt = 0;

		tagActcnt   = (int)rList.stream().filter(t -> t.getAct() == 2).count() 
				     + (int)rList2.stream().filter(t -> t.getAct() == 2).count() ;
		tagInActcnt = (int)rList.stream().filter(t ->  t.getAct() != 2).count() 
				     + (int)rList2.stream().filter(t ->  t.getAct() != 2).count();
		tagLowcnt = (int)rList.stream().filter(t ->  t.getBatt() < 3.5 ).count() 
			     + (int)rList2.stream().filter(t ->  t.getBatt() < 3.5).count();

		moteActcnt = (int) sList.stream().filter(t -> t.getAct() == 2).count() ;
		moteInActcnt = (int) sList.stream().filter(t -> t.getAct() != 2).count() ;
		moteLowcnt = (int) sList.stream().filter(t -> t.getBatt() < 3.5 ).count() ;

		lblApActive.setText(moteActcnt+"");
		lblApInactive.setText(moteInActcnt+"");
		lblApLow.setText(moteLowcnt+"");
		lblTagActive.setText(tagActcnt+"");
		lblTagInactive.setText(tagInActcnt+"");
		lblTagLow.setText(tagLowcnt+"");

//		MoteStatus mote =  sList.stream().filter(a -> a.getPlace() == 0 && a.getLoc() == 1 && a.getStand() == 1).findAny().orElse(new MoteStatus()) ;
//		sWidget[0].setSensor(  mote ) ;
//		mote = sList.stream().filter(a -> a.getPlace() == 0 && a.getLoc() == 3 && a.getStand() == 1).findAny().orElse(new MoteStatus()) ;
//		sWidget[1].setSensor( mote ) ;
//		mote =  sList.stream().filter(a -> a.getPlace() == 0 && a.getLoc() == 1 && a.getStand() == 2).findAny().orElse(new MoteStatus()) ;
//		sWidget[2].setSensor( mote ) ;
//		mote =  sList.stream().filter(a -> a.getPlace() == 0 && a.getLoc() == 3 && a.getStand() == 2).findAny().orElse(new MoteStatus()) ;
//		sWidget[3].setSensor( mote ) ;
//		mote =  sList.stream().filter(a -> a.getPlace() == 0 && a.getLoc() == 2 && a.getStand() == 1).findAny().orElse(new MoteStatus()) ;
//		sWidget[4].setSensor( mote ) ;
//		mote =  sList.stream().filter(a -> a.getPlace() == 0 && a.getLoc() == 4 && a.getStand() == 1).findAny().orElse(new MoteStatus()) ;
//		sWidget[5].setSensor( mote ) ;
//		mote =  sList.stream().filter(a -> a.getPlace() == 0 && a.getLoc() == 2 && a.getStand() == 2).findAny().orElse(new MoteStatus()) ;
//		sWidget[6].setSensor( mote ) ;
//		mote =  sList.stream().filter(a -> a.getPlace() == 0 && a.getLoc() == 4 && a.getStand() == 2).findAny().orElse(new MoteStatus()) ;
//		sWidget[7].setSensor( mote ) ;
		
//
//		mote =  sList.stream().filter(a -> a.getPlace() != 0 && a.getLoc() == 1 && a.getStand() == 1).findAny().orElse(new MoteStatus()) ;
//		sWidget2[0].setSensor(  mote ) ;
//		mote = sList.stream().filter(a -> a.getPlace() != 0 && a.getLoc() == 3 && a.getStand() == 1).findAny().orElse(new MoteStatus()) ;
//		sWidget2[1].setSensor( mote ) ;
//		mote =  sList.stream().filter(a -> a.getPlace() != 0 && a.getLoc() == 1 && a.getStand() == 2).findAny().orElse(new MoteStatus()) ;
//		sWidget2[2].setSensor( mote ) ;
//		mote =  sList.stream().filter(a -> a.getPlace() != 0 && a.getLoc() == 3 && a.getStand() == 2).findAny().orElse(new MoteStatus()) ;
//		sWidget2[3].setSensor( mote ) ;
//		mote =  sList.stream().filter(a -> a.getPlace() != 0 && a.getLoc() == 2 && a.getStand() == 1).findAny().orElse(new MoteStatus()) ;
//		sWidget2[4].setSensor( mote ) ;
//		mote =  sList.stream().filter(a -> a.getPlace() != 0 && a.getLoc() == 4 && a.getStand() == 1).findAny().orElse(new MoteStatus()) ;
//		sWidget2[5].setSensor( mote ) ;
//		mote =  sList.stream().filter(a -> a.getPlace() != 0 && a.getLoc() == 2 && a.getStand() == 2).findAny().orElse(new MoteStatus()) ;
//		sWidget2[6].setSensor( mote ) ;
//		mote =  sList.stream().filter(a -> a.getPlace() != 0 && a.getLoc() == 4 && a.getStand() == 2).findAny().orElse(new MoteStatus()) ;
//		sWidget2[7].setSensor( mote ) ;
		
		for ( int i = 0 ;i < sWidget.length ; i++) {
			sWidget[i].setValues();
//			sWidget2[i].setValues((short)1);
		}
		
		for (int i = 0; i < rList.size() ; i++ ) {
			int x = 1 ;
			if (rList.get(i).getAct() == 2) { 
				if (rList.get(i).getBatt() < 3.5)
					x = 0;
				else
					x = 2;
			}
			rWidget[i].setImage(rList.get(i).getDispNm() , x);
		}

		rList2.stream().forEach(a -> { if (a.getPlace() == 0 )  rListF.add(a); else rListR.add(a); } );

//		AppMain.delWidget(comp_r);
//		AppMain.delWidget(comp_r2);
//
//		rWidgetF = new RepeatWidget[ rListF.size() ] ;
//		System.out.println(rWidgetF.length);
		
		for (int i = 0 ;i < rWidgetF.length ; i++) {
//			rWidgetF[i] = new RepeatWidget(comp_r, rListF.get(i).getDispNm(), 0, 0);
			rWidgetF[i].setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
			int x = 1 ;
			if (rListF.get(i).getAct() == 2) { 
				if (rListF.get(i).getBatt() < 3.5)
					x = 0;
				else
					x = 2;
			}
			rWidgetF[i].setImage(rListF.get(i).getDispNm(),x);
		}

//		rWidgetR = new RepeatWidget[ rListR.size() ] ;
		
		for (int i = 0 ;i < rWidgetR.length ; i++) {
//			rWidgetR[i] = new RepeatWidget(comp_r2, rListR.get(i).getDispNm(), 0, 0);
			rWidgetR[i].setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
			int x = 1 ;
			if (rListR.get(i).getAct() == 2) { 
				if (rListR.get(i).getBatt() < 3.5)
					x = 0;
				else
					x = 2;
			}
			rWidgetR[i].setImage(rListR.get(i).getDispNm(), x);
		}
		
		
	}
}
