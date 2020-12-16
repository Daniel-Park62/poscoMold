package mold.posco.part;

import java.sql.Timestamp;

import javax.persistence.EntityManager;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import mold.posco.model.MoteInfo;
import mold.posco.model.MoteStatus;
import mold.posco.model.Vsensordata;

public class SensorWidget2  {

	Image img_act = SWTResourceManager.getImage( "images/blue.png");
	Image img_inact = SWTResourceManager.getImage( "images/grey.png");
	Image img_low = SWTResourceManager.getImage( "images/yellow.png");
	Image img_ob = SWTResourceManager.getImage( "images/red.png");

	String sid = "S00" ;
	Label lbl_temp ;
	Label lbl_humi ;
	Label lbl ;
	MoteStatus mote ;
	Composite composit_l, composit ;

	final Color colact = SWTResourceManager.getColor(49,136,248) ;
	final Color colinact = SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY) ;
	final Color collow = SWTResourceManager.getColor(245,174,0) ;
	final Color colout = SWTResourceManager.getColor(SWT.COLOR_RED) ;

	public MoteStatus getSensor() {
		return mote;
	}

	public void setSensor(MoteStatus sensor) {
		
		this.mote = sensor ;
		this.sid = sensor.getSensorNm() ;

	}

	/**
	 * @wbp.parser.constructor
	 */

	public SensorWidget2(Composite parent, MoteStatus sensor ) {
//		super(parent, SWT.NONE);
		this.mote = sensor ;
		
//		composit_l = new Composite(parent, SWT.NONE) ;
//		composit_l.setBackground(colinact);
//		composit_l.setLayoutData(new GridData(SWT.FILL , SWT.FILL, true,true));
//		GridLayout gl0 = new GridLayout(1,true) ;
//		gl0.marginTop = 20 ;
//		composit_l.setLayout(gl0);
		composit = new Composite(parent, SWT.NONE) ;
		GridLayout gl = new GridLayout(1,true) ;
//		gl.marginWidth = 20 ;
		gl.marginTop = 35 ;
		gl.marginBottom = 40 ;
		composit.setLayout(gl);
		
//		composit.setSize();

		composit.setLayoutData(new GridData(SWT.FILL , SWT.FILL, true,true));
		composit.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lbl_temp = new Label(composit, SWT.CENTER);
		lbl_humi = new Label(composit, SWT.CENTER);
		lbl = new Label(composit, SWT.RIGHT);

		lbl_temp.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE));
		lbl_temp.setText(String.format("%.2f ", 0.0 ) + "℃");

		lbl_humi.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE));
		lbl_humi.setText(String.format("%.2f ", 0.0) + "%");


		lbl_temp.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lbl_temp.setLayoutData(new GridData(SWT.CENTER , SWT.CENTER, true,true));
		lbl_humi.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lbl_humi.setLayoutData(new GridData(SWT.CENTER , SWT.CENTER, true,true));


		lbl.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE));
		lbl.setText(sid);
		lbl.setLayoutData(new GridData(SWT.CENTER , SWT.CENTER, true,true));

		lbl.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));

		lbl_temp.setFont(AppMain.fontT);
		lbl_humi.setFont(AppMain.fontT);
		lbl.setFont(SWTResourceManager.getFont( "Microsoft Sans Serif", 13, SWT.BOLD));
		lbl_temp.requestLayout();
		lbl_humi.requestLayout();
		lbl_temp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				chartView(0, mote.getDesc()  );
			}
		});
		
		lbl_humi.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				chartView(1, mote.getDesc() );
			}
		});
		lbl_temp.setCursor(AppMain.handc);
		lbl_humi.setCursor(AppMain.handc);

	}
	private void chartView(int thgb, String sno) {

		AppMain.delWidget(AppMain.cur_comp);
		AppMain.cur_comp.setLayout(new FillLayout());
		new RealChart(AppMain.cur_comp, SWT.NONE, thgb, sno);
		AppMain.cur_comp.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		AppMain.cur_comp.setSize(AppMain.cur_comp.getParent().getSize());
		AppMain.cur_comp.getParent().layout();
		AppMain.cur_comp.setToolTipText("Activechart");
	}
	
	public void setImage() {
		int w = composit.getSize().x ;
		int h = composit.getSize().y ;

		if ( composit.getBackgroundImage() != null) composit.getBackgroundImage().dispose();
		if (mote.getAct() == 2  && mote.getPlace() == AppMain.PLACE ) {
			if (mote.getObcnt() > 0)
//				composit_l.setBackground(colout);
				composit.setBackgroundImage(resize( img_ob, w,h ));
			else if (mote.getBatt() < 3.5) 
//				composit_l.setBackground(collow);
				composit.setBackgroundImage(resize(img_low, w,h ));
			else 
//				composit_l.setBackground(colact);
				composit.setBackgroundImage(resize(img_act, w,h ));
		} else {
//			composit_l.setBackground(colinact);
			composit.setBackgroundImage(resize(img_inact, w,h ));
		}
	}
	public void setValues( ) {
		if (mote == null) return ;
		double temp = 0, humi = 0 ;
		EntityManager em = AppMain.emf.createEntityManager() ;
		Timestamp time_c = em.createNamedQuery("LasTime.findone", Timestamp.class)
				.setParameter("id", mote.getPlace() ).getSingleResult() ;
		
		try {
			MoteInfo motei = em.createQuery("select t from MoteInfo t where t.place = :place "
					+ "  and t.tm = :tm and t.seq = :seq", MoteInfo.class)
					.setParameter("place", mote.getPlace())
					.setParameter("tm", time_c)
					.setParameter("seq", mote.getSeq())
					.getSingleResult() ;
			if ("0".contentEquals(AppMain.MOTECNF.getAdgb()) ) { 
				temp = motei.getTemp2() ;
				humi = motei.getHumi2() ;
			}else {
				temp = motei.getTemp() ;
				humi = motei.getHumi() ;
			}
			if ( AppMain.MOTECNF.getMaxhumi() < humi || AppMain.MOTECNF.getMinhumi() > humi 
					|| AppMain.MOTECNF.getMaxtemp() < temp || AppMain.MOTECNF.getMintemp() > temp) 
				mote.setObcnt( 1);
			else
				mote.setObcnt( 0);
			
		} catch (Exception e) {
			mote.setObcnt( 0);
//			if ( mote.getSeq() != 0 ) 
//				System.out.println(place + ":" + time_c + ":" + mote.getSeq() + " " + e );
		}
		
		lbl.setText("  " + mote.getDesc() + " / " + mote.getMold() + (mote.getPlace() == 0 ? "(공장)" : "(수리장)"));
		lbl_temp.setText(String.format("%.2f ", temp ) + "℃");
		lbl_humi.setText(String.format("%.2f ", humi ) + "%");
		lbl_temp.requestLayout();
		lbl_humi.requestLayout();
		lbl.requestLayout();
		setImage();
		em.close();
	}
	public void setValues(short place ) {
		if (mote == null) return ;
		double temp = 0, humi = 0 ;
		EntityManager em = AppMain.emf.createEntityManager() ;
		Timestamp time_c = em.createNamedQuery("LasTime.findone", Timestamp.class)
				.setParameter("id", place).getSingleResult() ;
		
		try {
			MoteInfo motei = em.createQuery("select t from MoteInfo t where t.place = :place "
					+ "  and t.tm = :tm and t.seq = :seq", MoteInfo.class)
					.setParameter("place", place)
					.setParameter("tm", time_c)
					.setParameter("seq", mote.getSeq())
					.getSingleResult() ;
			if ("0".contentEquals(AppMain.MOTECNF.getAdgb()) ) { 
				temp = motei.getTemp2() ;
				humi = motei.getHumi2() ;
			}else {
				temp = motei.getTemp() ;
				humi = motei.getHumi() ;
			}
			if ( AppMain.MOTECNF.getMaxhumi() < humi || AppMain.MOTECNF.getMinhumi() > humi 
					|| AppMain.MOTECNF.getMaxtemp() < temp || AppMain.MOTECNF.getMintemp() > temp) 
				mote.setObcnt( 1);
			else
				mote.setObcnt( 0);
			
		} catch (Exception e) {
			mote.setObcnt( 0);
//			if ( mote.getSeq() != 0 ) 
//				System.out.println(place + ":" + time_c + ":" + mote.getSeq() + " " + e );
		}
		
		lbl.setText("  " + mote.getDesc() + " / " + mote.getMold());
		lbl_temp.setText(String.format("%.2f ", temp ) + "℃");
		lbl_humi.setText(String.format("%.2f ", humi ) + "%");
		lbl_temp.requestLayout();
		lbl_humi.requestLayout();
		lbl.requestLayout();
		setImage();
		em.close();
	}
	
	public void setSid(String sid) {
		this.sid = sid ;
	}
	private Image resize(Image image, int width, int height) {
		  Image scaled = new Image(Display.getCurrent(), width, height);
		  GC gc = new GC(scaled);
		  gc.setAntialias(SWT.ON);
		  gc.setInterpolation(SWT.HIGH);
		  gc.drawImage(image, 0, 0,image.getBounds().width, image.getBounds().height, 0, 0, width, height);
		  gc.dispose();
//		  image.dispose(); // don't forget about me!
		  return scaled;
		}
}