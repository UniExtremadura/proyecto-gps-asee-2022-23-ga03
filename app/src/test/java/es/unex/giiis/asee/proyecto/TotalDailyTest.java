package es.unex.giiis.asee.proyecto;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import es.unex.giiis.asee.proyecto.recipesmodel.Daily;
import es.unex.giiis.asee.proyecto.recipesmodel.TotalDaily;

public class TotalDailyTest {

    private TotalDaily item;

    @Before
    public void init() {
        item = new TotalDaily();

        Daily daily1 = new Daily();
        daily1.setLabel("ca");
        item.setCa(daily1);

        Daily daily2 = new Daily();
        daily2.setLabel("chocdf");
        item.setChocdf(daily2);

        Daily daily3 = new Daily();
        daily3.setLabel("chole");
        item.setChole(daily3);

        Daily daily4 = new Daily();
        daily4.setLabel("enerckal");
        item.setEnercKcal(daily4);

        Daily daily5 = new Daily();
        daily5.setLabel("fasat");
        item.setFasat(daily5);

        Daily daily6 = new Daily();
        daily6.setLabel("fe");
        item.setFe(daily6);

        Daily daily7 = new Daily();
        daily7.setLabel("fibtg");
        item.setFibtg(daily7);

        Daily daily8 = new Daily();
        daily8.setLabel("foldfe");
        item.setFoldfe(daily8);

        Daily daily9 = new Daily();
        daily9.setLabel("k");
        item.setK(daily9);

        Daily daily10 = new Daily();
        daily10.setLabel("mg");
        item.setMg(daily10);

        Daily daily11 = new Daily();
        daily11.setLabel("na");
        item.setNa(daily11);

        Daily daily12 = new Daily();
        daily12.setLabel("nia");
        item.setNia(daily12);

        Daily daily13 = new Daily();
        daily13.setLabel("p");
        item.setP(daily13);

        Daily daily14 = new Daily();
        daily14.setLabel("fat");
        item.setFat(daily14);

        Daily daily15 = new Daily();
        daily15.setLabel("procnt");
        item.setProcnt(daily15);

        Daily daily16 = new Daily();
        daily16.setLabel("ribf");
        item.setRibf(daily16);

        Daily daily17 = new Daily();
        daily17.setLabel("thia");
        item.setThia(daily17);

        Daily daily18 = new Daily();
        daily18.setLabel("tocpha");
        item.setTocpha(daily18);

        Daily daily19 = new Daily();
        daily19.setLabel("vitaRae");
        item.setVitaRae(daily19);

        Daily daily20 = new Daily();
        daily20.setLabel("vitb6a");
        item.setVitb6a(daily20);

        Daily daily21 = new Daily();
        daily21.setLabel("vitb12");
        item.setVitb12(daily21);

        Daily daily22 = new Daily();
        daily22.setLabel("vitc");
        item.setVitc(daily22);

        Daily daily23 = new Daily();
        daily23.setLabel("vitd");
        item.setVitd(daily23);

        Daily daily24 = new Daily();
        daily24.setLabel("vitk1");
        item.setVitk1(daily24);

        Daily daily25 = new Daily();
        daily25.setLabel("zn");
        item.setZn(daily25);
    }

    @Test
    public void getEnercKcalTest() {
        assertEquals("enerckal", item.getEnercKcal().getLabel());
    }

    @Test
    public void setEnercKcalTest() {
        assertEquals("enerckal", item.getEnercKcal().getLabel());
        Daily daily = new Daily();
        daily.setLabel("enerckal1");
        item.setEnercKcal(daily);
        assertEquals("enerckal1", item.getEnercKcal().getLabel());
    }

    @Test
    public void getFatTest() {
        assertEquals("fat", item.getFat().getLabel());
    }

    @Test
    public void setFatTest() {
        assertEquals("fat", item.getFat().getLabel());
        Daily daily = new Daily();
        daily.setLabel("fat1");
        item.setFat(daily);
        assertEquals("fat1", item.getFat().getLabel());
    }

    @Test
    public void getFasatTest() {
        assertEquals("fasat", item.getFasat().getLabel());
    }

    @Test
    public void setFasatTest() {
        assertEquals("fasat", item.getFasat().getLabel());
        Daily daily = new Daily();
        daily.setLabel("fasat1");
        item.setFasat(daily);
        assertEquals("fasat1", item.getFasat().getLabel());
    }

    @Test
    public void getChocdfTest() {
        assertEquals("chocdf", item.getChocdf().getLabel());
    }

    @Test
    public void setChocdfTest() {
        assertEquals("chocdf", item.getChocdf().getLabel());
        Daily daily = new Daily();
        daily.setLabel("chocdf1");
        item.setChocdf(daily);
        assertEquals("chocdf1", item.getChocdf().getLabel());
    }

    @Test
    public void getFibtgTest() {
        assertEquals("fibtg", item.getFibtg().getLabel());
    }

    @Test
    public void setFibtgTest() {
        assertEquals("fibtg", item.getFibtg().getLabel());
        Daily daily = new Daily();
        daily.setLabel("fibtg1");
        item.setFibtg(daily);
        assertEquals("fibtg1", item.getFibtg().getLabel());
    }

    @Test
    public void getProcntTest() {
        assertEquals("procnt", item.getProcnt().getLabel());
    }

    @Test
    public void setProcntTest() {
        assertEquals("procnt", item.getProcnt().getLabel());
        Daily daily = new Daily();
        daily.setLabel("procnt1");
        item.setProcnt(daily);
        assertEquals("procnt1", item.getProcnt().getLabel());
    }

    @Test
    public void getCholeTest() {
        assertEquals("chole", item.getChole().getLabel());
    }

    @Test
    public void setCholeTest() {
        assertEquals("chole", item.getChole().getLabel());
        Daily daily = new Daily();
        daily.setLabel("chole1");
        item.setChole(daily);
        assertEquals("chole1", item.getChole().getLabel());
    }

    @Test
    public void getNaTest() {
        assertEquals("na", item.getNa().getLabel());
    }

    @Test
    public void setNaTest() {
        assertEquals("na", item.getNa().getLabel());
        Daily daily = new Daily();
        daily.setLabel("na1");
        item.setNa(daily);
        assertEquals("na1", item.getNa().getLabel());
    }

    @Test
    public void getCaTest() {
        assertEquals("ca", item.getCa().getLabel());
    }

    @Test
    public void setCaTest() {
        assertEquals("ca", item.getCa().getLabel());
        Daily daily = new Daily();
        daily.setLabel("ca1");
        item.setCa(daily);
        assertEquals("ca1", item.getCa().getLabel());
    }

    @Test
    public void getMgTest() {
        assertEquals("mg", item.getMg().getLabel());
    }

    @Test
    public void setMgTest() {
        assertEquals("mg", item.getMg().getLabel());
        Daily daily = new Daily();
        daily.setLabel("mg1");
        item.setMg(daily);
        assertEquals("mg1", item.getMg().getLabel());
    }

    @Test
    public void getKTest() {
        assertEquals("k", item.getK().getLabel());
    }

    @Test
    public void setKTest() {
        assertEquals("k", item.getK().getLabel());
        Daily daily = new Daily();
        daily.setLabel("k1");
        item.setK(daily);
        assertEquals("k1", item.getK().getLabel());
    }

    @Test
    public void getFeTest() {
        assertEquals("fe", item.getFe().getLabel());
    }

    @Test
    public void setFeTest() {
        assertEquals("fe", item.getFe().getLabel());
        Daily daily = new Daily();
        daily.setLabel("fe1");
        item.setFe(daily);
        assertEquals("fe1", item.getFe().getLabel());
    }

    @Test
    public void getZnTest() {
        assertEquals("zn", item.getZn().getLabel());
    }

    @Test
    public void setZnTest() {
        assertEquals("zn", item.getZn().getLabel());
        Daily daily = new Daily();
        daily.setLabel("zn1");
        item.setZn(daily);
        assertEquals("zn1", item.getZn().getLabel());
    }

    @Test
    public void getPTest() {
        assertEquals("p", item.getP().getLabel());
    }

    @Test
    public void setPTest() {
        assertEquals("p", item.getP().getLabel());
        Daily daily = new Daily();
        daily.setLabel("p1");
        item.setP(daily);
        assertEquals("p1", item.getP().getLabel());
    }

    @Test
    public void getVitaRaeTest() {
        assertEquals("vitaRae", item.getVitaRae().getLabel());
    }

    @Test
    public void setVitaRaeTest() {
        assertEquals("vitaRae", item.getVitaRae().getLabel());
        Daily daily = new Daily();
        daily.setLabel("vitaRae1");
        item.setVitaRae(daily);
        assertEquals("vitaRae1", item.getVitaRae().getLabel());
    }

    @Test
    public void getVitcTest() {
        assertEquals("vitc", item.getVitc().getLabel());
    }

    @Test
    public void setVitcTest() {
        assertEquals("vitc", item.getVitc().getLabel());
        Daily daily = new Daily();
        daily.setLabel("vitc1");
        item.setVitc(daily);
        assertEquals("vitc1", item.getVitc().getLabel());
    }

    @Test
    public void getThiaTest() {
        assertEquals("thia", item.getThia().getLabel());
    }

    @Test
    public void setThiaTest() {
        assertEquals("thia", item.getThia().getLabel());
        Daily daily = new Daily();
        daily.setLabel("thia1");
        item.setThia(daily);
        assertEquals("thia1", item.getThia().getLabel());
    }

    @Test
    public void getRibfTest() {
        assertEquals("ribf", item.getRibf().getLabel());
    }

    @Test
    public void setRibfTest() {
        assertEquals("ribf", item.getRibf().getLabel());
        Daily daily = new Daily();
        daily.setLabel("ribf1");
        item.setRibf(daily);
        assertEquals("ribf1", item.getRibf().getLabel());
    }

    @Test
    public void getNiaTest() {
        assertEquals("nia", item.getNia().getLabel());
    }

    @Test
    public void setNiaTest() {
        assertEquals("nia", item.getNia().getLabel());
        Daily daily = new Daily();
        daily.setLabel("nia1");
        item.setNia(daily);
        assertEquals("nia1", item.getNia().getLabel());
    }

    @Test
    public void getVitb6aTest() {
        assertEquals("vitb6a", item.getVitb6a().getLabel());
    }

    @Test
    public void setVitb6aTest() {
        assertEquals("vitb6a", item.getVitb6a().getLabel());
        Daily daily = new Daily();
        daily.setLabel("vitb6a1");
        item.setVitb6a(daily);
        assertEquals("vitb6a1", item.getVitb6a().getLabel());
    }

    @Test
    public void getFoldfeTest() {
        assertEquals("foldfe", item.getFoldfe().getLabel());
    }

    @Test
    public void setFoldfeTest() {
        assertEquals("foldfe", item.getFoldfe().getLabel());
        Daily daily = new Daily();
        daily.setLabel("foldfe1");
        item.setFoldfe(daily);
        assertEquals("foldfe1", item.getFoldfe().getLabel());
    }

    @Test
    public void getVitb12Test() {
        assertEquals("vitb12", item.getVitb12().getLabel());
    }

    @Test
    public void setVitb12Test() {
        assertEquals("vitb12", item.getVitb12().getLabel());
        Daily daily = new Daily();
        daily.setLabel("vitb121");
        item.setVitb12(daily);
        assertEquals("vitb121", item.getVitb12().getLabel());
    }

    @Test
    public void getVitdTest() {
        assertEquals("vitd", item.getVitd().getLabel());
    }

    @Test
    public void setVitdTest() {
        assertEquals("vitd", item.getVitd().getLabel());
        Daily daily = new Daily();
        daily.setLabel("vitd1");
        item.setVitd(daily);
        assertEquals("vitd1", item.getVitd().getLabel());
    }

    @Test
    public void getTocphaTest() {
        assertEquals("tocpha", item.getTocpha().getLabel());
    }

    @Test
    public void setTocphaTest() {
        assertEquals("tocpha", item.getTocpha().getLabel());
        Daily daily = new Daily();
        daily.setLabel("tocpha1");
        item.setTocpha(daily);
        assertEquals("tocpha1", item.getTocpha().getLabel());
    }

    @Test
    public void getVitk1Test() {
        assertEquals("vitk1", item.getVitk1().getLabel());
    }

    @Test
    public void setVitk1Test() {
        assertEquals("vitk1", item.getVitk1().getLabel());
        Daily daily = new Daily();
        daily.setLabel("vitk11");
        item.setVitk1(daily);
        assertEquals("vitk11", item.getVitk1().getLabel());
    }
}
