package mold.posco.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import com.ibm.icu.util.Calendar;

import mold.posco.part.AppMain;

public class FindMoteInfo {

	
	private ArrayList<MoteInfo> tempList ;
	private ArrayList<MoteHist> tempList2 ;
	private ArrayList<ChartData> chList ;
//    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    private DateFormat dateFmt1 = new SimpleDateFormat("yyyy-MM-dd");
//    private DateFormat dateFmt2 = new SimpleDateFormat("HH:mm:ss");

    public ArrayList<MoteInfo> getMoteInfos(String fmdt, String todt, int seq) {
    	Timestamp ts_fmdt = Timestamp.valueOf(fmdt) ;
    	Timestamp ts_todt = Timestamp.valueOf(todt) ;

    	return getMoteInfos(ts_fmdt, ts_todt, seq)  ;
    }

    public ArrayList<MoteHist> getMoteHists
           (String fmdt, String todt, int seq, short place) {
    	Timestamp ts_fmdt = Timestamp.valueOf(fmdt) ;
    	Timestamp ts_todt = Timestamp.valueOf(todt) ;

    	return getMoteHists(ts_fmdt, ts_todt, seq,  place)  ;
    }
    
    public String[] getSensorList() {
    	List<String> slist = new ArrayList() ;
    	EntityManager em = AppMain.emf.createEntityManager();
    	em.createQuery("select m.desc  "
    			+ "from MoteStatus m where m.gubun = 'S' and m.spare = 'N' order by m.seq ")
    		.getResultList().stream().forEach(m -> slist.add(m.toString()));
    	String[] sresult = new String[slist.size()] ;
    	int ix = 0;
    	for (String ss : slist) {
			sresult[ix++] =  ss ;
		}
    	return sresult ;
    }

    public ArrayList<MoteInfo> getMoteInfos(Timestamp fmdt, Timestamp todt, int seq) {
		tempList = new ArrayList<MoteInfo>();
		EntityManager em = AppMain.emf.createEntityManager();
		em.clear();
		em.getEntityManagerFactory().getCache().evictAll();
		String sseqif ;
		if (seq == 0) {
			sseqif = "";
		} else {
			sseqif = "and t.seq = " + seq ;
		}
		
        TypedQuery<MoteInfo> qMotes = em.createQuery("select t from MoteInfo t " 
        		+ "where t.tm between :fmdt and :todt " + sseqif + " order by t.tm ,t.seq ", MoteInfo.class);

        qMotes.setParameter("fmdt", fmdt);
        qMotes.setParameter("todt", todt);
        qMotes.getResultList().stream().forEach( t -> tempList.add(t));
        
		em.close();

		return tempList ;

    }

    public ArrayList<MoteHist> getMoteHists(Timestamp fmdt, Timestamp todt, int seq, short place) {
		tempList2 = new ArrayList<MoteHist>();
		EntityManager em = AppMain.emf.createEntityManager();
		em.clear();
		em.getEntityManagerFactory().getCache().evictAll();
		String sseqif ;
		if (seq == 0) {
			sseqif = "";
		} else {
			sseqif = "and t.seq = " + seq ;
		}

        TypedQuery<MoteHist> qMotes = em.createQuery("select t from MoteHist t, MoteConfig c " 
        		+ "where t.tm between :fmdt and :todt and t.place = :place " + sseqif + " order by t.tm desc ,t.seq asc", MoteHist.class);

        qMotes.setParameter("fmdt", fmdt);
        qMotes.setParameter("todt", todt);
        qMotes.setParameter("place", place);
        qMotes.setHint(QueryHints.READ_ONLY, HintValues.TRUE);
        qMotes.getResultList().stream().forEach( t -> tempList2.add(t));
        
		em.close();

		return tempList2 ;

    }

    public List<PeakTrend> getPeakTrend(int seq) {

		EntityManager em = AppMain.emf.createEntityManager();
    	Timestamp todt, fmdt ;
		todt = em.createNamedQuery("LasTime.findone", Timestamp.class)
				.setParameter("id", AppMain.PLACE).getSingleResult() ;

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(todt.getTime()); 
		cal.add(Calendar.HOUR, -24); 
		
		fmdt = new Timestamp(cal.getTime().getTime());
		
		String sql = " select tm, maxval - minval as val from peaktrend where tm >= '" 
		            + fmdt + "' and seq = "  + seq  ; 
 
		Query nativeQuery = em.createNativeQuery(sql);
	    List<Object[]> resultList = nativeQuery.getResultList();
	    List<PeakTrend> chList = resultList.stream().map(r -> new PeakTrend( 
	    		Timestamp.valueOf(r[0].toString()) , ((Long)r[1]).intValue()  ))
	    		.collect(Collectors.toCollection(ArrayList::new)) ;

        
		em.close();

		return chList ;

    }

    public ArrayList<ChartData> getChartData(Timestamp fmdt, Timestamp todt, int seq, int thgb) {
		EntityManager em = AppMain.emf.createEntityManager();
		em.clear();
//		em.getEntityManagerFactory().getCache().evictAll();
		
		String sql = " select a.tm, a.seq, a.place, case when adgb = '0' then temp2 else temp end temp,"
				+ " case when adgb = '0' then humi2 else humi end humi, b.descript from moteinfo a join motestatus b on a.seq = b.seq"
				+ ", (select adgb from moteconfig) x"
				+ " where tm between '" + fmdt + "' and '" + todt + "' " 
				+ ( seq > 0 ? " and a.seq = " + seq : "");
		
 
		Query nativeQuery = em.createNativeQuery(sql);
	    List<Object[]> resultList = nativeQuery.getResultList();
	    if (thgb == 0)
	    	chList = resultList.stream().map(r -> new ChartData( 
	    			Timestamp.valueOf(r[0].toString()) , ((Long)r[1]).intValue(), (int)r[2], (Double)r[3], r[5].toString() ))
	    	.collect(Collectors.toCollection(ArrayList::new)) ;
	    else
		    chList = resultList.stream().map(r -> new ChartData( 
		    		Timestamp.valueOf(r[0].toString()) , ((Long)r[1]).intValue(), (int)r[2], (Double)r[4],r[5].toString() ))
		    		.collect(Collectors.toCollection(ArrayList::new)) ;
	    	
        
		em.close();

		return chList ;

    }

    public ArrayList<ChartData> getChartData(int seq , int thgb, int sec) {
    	Timestamp todt, fmdt ;
		EntityManager em = AppMain.emf.createEntityManager();
		todt = em.createNamedQuery("LasTime.findone", Timestamp.class)
				.setParameter("id", AppMain.PLACE).getSingleResult() ;
		em.close();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(todt.getTime()); 
		cal.add(Calendar.SECOND, -sec); 
		
		fmdt = new Timestamp(cal.getTime().getTime());
		
    	return getChartData(fmdt, todt, seq, thgb)  ;
		
    }

    public ArrayList<ChartData> getChartData_hist(Timestamp fmdt, Timestamp todt, int seq, int thgb) {
		EntityManager em = AppMain.emf.createEntityManager();
		em.clear();
//		em.getEntityManagerFactory().getCache().evictAll();

		String sql = " select a.tm, a.seq, a.place, case when adgb = '0' then temp2 else temp end temp,"
				+ " case when adgb = '0' then humi2 else humi end humi, b.descript from motehist a join motestatus b on a.seq = b.seq"
				+ ", (select adgb from moteconfig) x"
				+ " where  tm between '" + fmdt + "' and '" + todt + "' " 
				+ ( seq > 0 ? " and a.seq = " + seq : "");
		
 
		Query nativeQuery = em.createNativeQuery(sql);
		
	    List<Object[]> resultList = nativeQuery.getResultList();
	    
	    if (thgb == 0)
	    	chList = resultList.stream().map(r -> new ChartData( 
	    			Timestamp.valueOf(r[0].toString()) , ((Long)r[1]).intValue(), (int)r[2], (Double)r[3],r[5].toString() ))
	    	.collect(Collectors.toCollection(ArrayList::new)) ;
	    else
		    chList = resultList.stream().map(r -> new ChartData( 
		    		Timestamp.valueOf(r[0].toString()) , ((Long)r[1]).intValue(), (int)r[2], (Double)r[4],r[5].toString() ))
		    		.collect(Collectors.toCollection(ArrayList::new)) ;
	    	
		em.close();

		return chList ;

    }

    public ArrayList<ChartData> getChartData_hist(int seq , int thgb, int sec) {
    	Timestamp todt, fmdt ;
		EntityManager em = AppMain.emf.createEntityManager();
		todt = em.createNamedQuery("LasTime.findone", Timestamp.class)
				.setParameter("id", AppMain.PLACE).getSingleResult() ;
		em.close();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(todt.getTime()); 
		cal.add(Calendar.SECOND, -sec); 
		
		fmdt = new Timestamp(cal.getTime().getTime());
		
    	return getChartData_hist(fmdt, todt, seq, thgb)  ;
		
    }

    public ArrayList<MoteInfo> getMoteInfos(int seq, int cnt) {
    	Timestamp todt, fmdt ;
		EntityManager em = AppMain.emf.createEntityManager();
		todt = em.createNamedQuery("LasTime.findone", Timestamp.class)
				.setParameter("id", AppMain.PLACE).getSingleResult() ;
		em.close();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(todt.getTime()); 
		cal.add(Calendar.SECOND, -cnt); 
		
		fmdt = new Timestamp(cal.getTime().getTime());
		
    	return getMoteInfos(fmdt, todt, seq)  ;
		
    }
    
    public Timestamp getLasTime() {
    	Timestamp lstime ;
		EntityManager em = AppMain.emf.createEntityManager();
		lstime = em.createNamedQuery("LasTime.findone", Timestamp.class)
				.setParameter("id", AppMain.PLACE).getSingleResult() ;
		em.close();
    	return lstime  ;
    }

    public int getMoteSeq(String desc) {
    	int seq ;
    	if ("ALL".equals(desc)) return 0;
		EntityManager em = AppMain.emf.createEntityManager();
		seq = em.createQuery("select m.seq from MoteStatus m where m.desc = :desc", Integer.class)
				.setParameter("desc", desc).getSingleResult() ;
		em.close();
    	return seq  ;
    }
}