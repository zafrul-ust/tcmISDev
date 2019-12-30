package com.tcmis.client.report.process;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.*;
import java.util.Iterator;
import java.util.Locale;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Font;
import java.awt.BasicStroke;
import java.awt.Stroke;

import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.TextAnchor;

import org.jfree.chart.title.*;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LayeredBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.*;
import org.jfree.chart.*;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.urls.TimeSeriesURLGenerator;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.category.*;
import com.tcmis.client.report.beans.DailyPartInventorySumViewBean;
import com.tcmis.client.report.beans.DailyPartIssueSumViewBean;
import com.tcmis.client.report.beans.DailyPartReceiptSumViewBean;
import com.tcmis.client.report.beans.IgCountInventoryViewBean;
import com.tcmis.client.report.beans.PartYearlyLeadTimeViewBean;
import com.tcmis.client.report.beans.PartMrYearlyLeadTimeViewBean;
import com.tcmis.client.report.beans.PartSupYearlyLeadTimeViewBean;
import com.tcmis.client.report.factory.DailyPartInventorySumViewBeanFactory;
import com.tcmis.client.report.factory.DailyPartIssueSumViewBeanFactory;
import com.tcmis.client.report.factory.DailyPartReceiptSumViewBeanFactory;
import com.tcmis.client.report.factory.IgCountInventoryViewBeanFactory;
import com.tcmis.client.report.factory.PartYearlyLeadTimeViewBeanFactory;
import com.tcmis.client.report.factory.PartMrYearlyLeadTimeViewBeanFactory;
import com.tcmis.client.report.factory.PartSupYearlyLeadTimeViewBeanFactory;
import com.tcmis.common.admin.beans.InventoryGroupDefinitionBean;
import com.tcmis.common.admin.factory.InventoryGroupDefinitionBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.*;

/**
 * <p>Title: </p>
 * <p>Description: Class to create inventory, issues, lead time, and receipts charts </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */

public class ChartProcess extends BaseProcess {

	//this will hold the html image map code
	private String map;
	private static final int IMAGE_WIDTH = 500;
	private static final int IMAGE_HEIGHT = 300;
	private static final int DASHBOARD_IMAGE_WIDTH = 160;
	private static final int DASHBOARD_IMAGE_HEIGHT = 300;
	private String chartUrl;
	private String chartDirectory;

	//private ResourceLibrary library;
	public ChartProcess(String client) {
		super(client);
		ResourceLibrary library = new ResourceLibrary("chart");
		chartUrl = library.getString("chart.image.url");
		chartDirectory = library.getString("chart.image.dir");
	}

	public String getMap() {
		return this.map;
	}

	/**
	 * ***************************************************************************
	 * Creates a chart of issues for the last year using jfreechart.<BR>
	 *
	 * @param inventoryGroup	  inventory group to query by
	 * @param partNumber			part number to query by
	 * @param partGroupNo		  part group number to query by
	 * @param inventoryGroupName inventory group to put in the label
	 * @param catalogId			 catalog id to query by
	 * @param issueGeneration	 to determine if an average should be plotted
	 * @returns name of the file that was created
	 * **************************************************************************
	 */
	public String generateIssuesChart(String inventoryGroup, String partNumber, String partGroupNo, String inventoryGroupName, String catalogId, String issueGeneration, String catalogCompanyId) {
		String filename = null;
		String packaging = null;
		boolean showLines = false;
		try {
			Collection graphData = this.getIssuesData(inventoryGroup, partNumber, partGroupNo, catalogId,catalogCompanyId);
			if (graphData == null || graphData.size() == 0) {
				throw new NoDataException("");
			}
			//DefaultTableXYDataset dataset = new DefaultTableXYDataset();
			XYSeriesCollection dataset = new XYSeriesCollection();
			XYSeries issueDataSeries = new XYSeries("Issue");
			Iterator iterator = graphData.iterator();
			DailyPartIssueSumViewBean previousBean = null;
			while (iterator.hasNext()) {
				DailyPartIssueSumViewBean bean = (DailyPartIssueSumViewBean) iterator.next();
				//check what kind of inventory group it is and determine if I need to average the issues
				if ("Item Counting".equalsIgnoreCase(issueGeneration)) {
					showLines = true;
					if (previousBean != null && bean.getQuantity().intValue() != 0) {
						BigDecimal daySpan = new BigDecimal((bean.getDailyDate().getTime() - previousBean.getDailyDate().
							 getTime()) / (1000 * 60 * 60 * 24));
						BigDecimal averageQuantity = bean.getQuantity().divide(daySpan, 3, BigDecimal.ROUND_HALF_UP);
						issueDataSeries.add(previousBean.getDailyDate().getTime() + 1, averageQuantity);
						issueDataSeries.add(bean.getDailyDate().getTime(), averageQuantity);
					}
					previousBean = bean;
				} else {
					//it's a picking inventory group so we'll just plot the values
					issueDataSeries.add(bean.getDailyDate().getTime(), bean.getQuantity());
				}
				packaging = bean.getPackaging();
			}
			dataset.addSeries(issueDataSeries);
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
			StandardXYToolTipGenerator ttg = new StandardXYToolTipGenerator(StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT, sdf, NumberFormat.getInstance());
			//  Create the chart object
			CategoryAxis categoryAxis = new CategoryAxis("Part");
			ValueAxis valueAxis = new NumberAxis("Inventory Level");
			//XYItemRenderer renderer = new XYBarRenderer();
			XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
			renderer.setShapesVisible(!showLines);
			renderer.setToolTipGenerator(ttg);
			renderer.setLinesVisible(showLines);
			Calendar oneYearAgo = Calendar.getInstance();
			oneYearAgo.add(Calendar.YEAR, -1);
			oneYearAgo.add(Calendar.DATE, -(oneYearAgo.get(Calendar.DATE)));
			Calendar endOfMonth = Calendar.getInstance();
			endOfMonth.set(Calendar.DATE, endOfMonth.getMaximum(Calendar.DATE) - 1);
			endOfMonth.set(Calendar.MONTH, endOfMonth.get(Calendar.MONTH));
			endOfMonth.set(Calendar.YEAR, endOfMonth.get(Calendar.YEAR));
			DateAxis dateAxis = new DateAxis();
			dateAxis.setRange(oneYearAgo.getTime(), endOfMonth.getTime());
			ValueAxis rangeAxis = new NumberAxis("Quantity (" + packaging + ")");
			Plot plot = new XYPlot(dataset, dateAxis, rangeAxis, renderer);
			JFreeChart chart = new JFreeChart("Issues for " + partNumber + " in " + inventoryGroupName, JFreeChart.DEFAULT_TITLE_FONT, plot, false);
			chart.setBackgroundPaint(java.awt.Color.white);
			//  Write the chart image to the temporary directory
			ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
			ResourceLibrary library = new ResourceLibrary("chart");
			//File tempDir = new File(directoryPath + library.getString("chart.image.dir"));
			File tempDir = new File(chartDirectory);
			File file = File.createTempFile("JFREE", ".png", tempDir);
			ChartUtilities.saveChartAsPNG(file, chart, IMAGE_WIDTH, IMAGE_HEIGHT, info);
			filename = file.getName();
			if (log.isDebugEnabled()) {
				log.debug("Filename:" + filename);
			}
			//  Write the image map
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ChartUtilities.writeImageMap(pw, filename, info, false);
			pw.flush();
			this.map = sw.toString();
		} catch (NoDataException e) {
			log.error("Issue graph query returned no data");
			filename = "public_nodata_500x300.png";
		} catch (Exception e) {
			log.error("Error creating issue graph.", e);
			filename = "public_error_500x300.png";
		}
		return filename;
	}

	/**
	 * ***************************************************************************
	 * Creates a chart of receipts for the last year using jfreechart.<BR>
	 *
	 * @param inventoryGroup	  inventory group to query by
	 * @param partNumber			part number to query by
	 * @param partGroupNo		  part group number to query by
	 * @param inventoryGroupName inventory group to put in the label
	 * @param catalogId			 catalog id to query by
	 * @returns name of the file that was created
	 * **************************************************************************
	 */
	public String generateReceiptChart(String inventoryGroup, String partNumber, String partGroupNo, String inventoryGroupName, String catalogId, String catalogCompanyId) {
		String filename = null;
		String packaging = null;
		try {
			Collection graphData = this.getReceiptData(inventoryGroup, partNumber, partGroupNo, catalogId,catalogCompanyId);
			if (graphData == null || graphData.size() == 0) {
				throw new NoDataException("");
			}
			XYSeries receiptDataSeries = new XYSeries("Receipt");
			Iterator iterator = graphData.iterator();
			while (iterator.hasNext()) {
				DailyPartReceiptSumViewBean bean = (DailyPartReceiptSumViewBean) iterator.next();
				receiptDataSeries.add(bean.getDailyDate().getTime(), bean.getQuantity());
				packaging = bean.getPackaging();
			}
			XYSeriesCollection xyDataset = new XYSeriesCollection();
			xyDataset.addSeries(receiptDataSeries);
			//  Create tooltip and URL generators
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
			StandardXYToolTipGenerator ttg = new StandardXYToolTipGenerator(StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT, sdf, NumberFormat.getInstance());
			TimeSeriesURLGenerator urlg = null;
			//TimeSeriesURLGenerator urlg = new TimeSeriesURLGenerator(sdf, "pie_chart.jsp", "series", "hitDate");
			//  Create the chart object
			Calendar oneYearAgo = Calendar.getInstance();
			oneYearAgo.add(Calendar.YEAR, -1);
			oneYearAgo.add(Calendar.DATE, -(oneYearAgo.get(Calendar.DATE)));
			Calendar endOfMonth = Calendar.getInstance();
			endOfMonth.set(Calendar.DATE, endOfMonth.getMaximum(Calendar.DATE) - 1);
			endOfMonth.set(Calendar.MONTH, endOfMonth.get(Calendar.MONTH));
			endOfMonth.set(Calendar.YEAR, endOfMonth.get(Calendar.YEAR));
			DateAxis dateAxis = new DateAxis();
			dateAxis.setRange(oneYearAgo.getTime(), endOfMonth.getTime());
			NumberAxis valueAxis = new NumberAxis("Quantity (" + packaging + ")");
			//valueAxis.setAutoRangeIncludesZero(false); // override default
			StandardXYItemRenderer renderer = new StandardXYItemRenderer(StandardXYItemRenderer.LINES + StandardXYItemRenderer.SHAPES, ttg, urlg);
			renderer.setShapesFilled(true);
			XYLineAndShapeRenderer renderer3 = new XYLineAndShapeRenderer();
			renderer3.setLinesVisible(false);
			renderer3.setToolTipGenerator(ttg);
			XYPlot plot = new XYPlot(xyDataset, dateAxis, valueAxis, renderer3);
			JFreeChart chart = new JFreeChart("Receipts for " + partNumber + " in " + inventoryGroupName, JFreeChart.DEFAULT_TITLE_FONT, plot, true);
			chart.setBackgroundPaint(java.awt.Color.white);
			//  Write the chart image to the temporary directory
			ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
			//File tempDir = new File(directoryPath + library.getString("chart.image.dir"));
			File tempDir = new File(chartDirectory);
			File file = File.createTempFile("JFREE", ".png", tempDir);
			ChartUtilities.saveChartAsPNG(file, chart, IMAGE_WIDTH, IMAGE_HEIGHT, info);
			filename = file.getName();
			//  Write the image map
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ChartUtilities.writeImageMap(pw, filename, info, false);
			pw.flush();
			this.map = sw.toString();
		} catch (NoDataException e) {
			log.error("Receipts chart returned no data.");
			filename = "public_nodata_500x300.png";
		} catch (Exception e) {
			log.error("Error creating receipt chart.", e);
			filename = "public_error_500x300.png";
		}
		return filename;
	}

	/**
	 * ***************************************************************************
	 * Creates a chart of inventory for the last year using jfreechart.<BR>
	 *
	 * @param inventoryGroup	  inventory group to query by
	 * @param partNumber			part number to query by
	 * @param partGroupNo		  part group number to query by
	 * @param inventoryGroupName inventory group to put in the label
	 * @param catalogId			 catalog id to query by
	 * @returns name of the file that was created
	 * **************************************************************************
	 */
	public String generateInventoryChart(String inventoryGroup, String partNumber, String partGroupNo, String inventoryGroupName, String catalogId, BigDecimal monthSpan, String catalogCompanyId) {
		String filename = null;
		String packaging = null;
		BigDecimal openBlanketPo = null;
		try {
			Collection graphData = this.getInventoryData(inventoryGroup, partNumber, partGroupNo, catalogId, monthSpan,catalogCompanyId);
			if (graphData == null || graphData.size() == 0) {
				throw new NoDataException("");
			}
			//  Create and populate an XYSeries Collection
			XYSeries inventoryDataSeries = new XYSeries("Inventory Level");
			XYSeries lowLevelAlarmDataSeries = new XYSeries("Low Level Alarm");
			XYSeries highLevelAlarmDataSeries = new XYSeries("High Level Alarm");
			XYSeries stockingLevelDataSeries = new XYSeries("Stocking Level");
			XYSeries reorderPointDataSeries = new XYSeries("Reorder Point");
			//XYSeries poConfirmedDataSeries = new XYSeries("PO (Open Amount)");
			Iterator iterator = graphData.iterator();
			while (iterator.hasNext()) {
				DailyPartInventorySumViewBean bean = (DailyPartInventorySumViewBean) iterator.next();
				if (bean.getQuantity() != null) {
					inventoryDataSeries.add(bean.getDailyDate().getTime(), bean.getQuantity());
				}
				lowLevelAlarmDataSeries.add(bean.getDailyDate().getTime(), bean.getLowAlarm());
				highLevelAlarmDataSeries.add(bean.getDailyDate().getTime(), bean.getHighAlarm());
				stockingLevelDataSeries.add(bean.getDailyDate().getTime(), bean.getStockingLevel());
				reorderPointDataSeries.add(bean.getDailyDate().getTime(), bean.getReorderPoint());

				if (bean.getOpenBlanketPo() != null) {
					openBlanketPo = bean.getOpenBlanketPo();
				}
				packaging = bean.getPackaging();
			}
			//check if there were any open blanket po's, if so change the label

			XYSeriesCollection xyDataset1 = new XYSeriesCollection();
			xyDataset1.addSeries(inventoryDataSeries);
			//XYSeriesCollection xyDataset2 = new XYSeriesCollection();
			//xyDataset2.addSeries(lowLevelAlarmDataSeries);
			//xyDataset2.addSeries(highLevelAlarmDataSeries);
			//xyDataset2.addSeries(stockingLevelDataSeries);
			//xyDataset2.addSeries(reorderPointDataSeries);
			//XYSeriesCollection xyDataset3 = new XYSeriesCollection();
			//xyDataset3.addSeries(poConfirmedDataSeries);
			XYSeriesCollection xyDataset4 = new XYSeriesCollection();
			xyDataset4.addSeries(stockingLevelDataSeries);
			XYSeriesCollection xyDataset5 = new XYSeriesCollection();
			xyDataset5.addSeries(lowLevelAlarmDataSeries);
			XYSeriesCollection xyDataset6 = new XYSeriesCollection();
			xyDataset6.addSeries(highLevelAlarmDataSeries);
			XYSeriesCollection xyDataset7 = new XYSeriesCollection();
			xyDataset7.addSeries(reorderPointDataSeries);
			//  Create tooltip and URL generators
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
			StandardXYToolTipGenerator ttg = new StandardXYToolTipGenerator(StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT, sdf, NumberFormat.getInstance());
			//TimeSeriesURLGenerator urlg = new TimeSeriesURLGenerator(sdf, "pie_chart.jsp", "series", "hitDate");
			TimeSeriesURLGenerator urlg = null;
			//  Create the chart object
			Calendar oneYearAgo = Calendar.getInstance();
			oneYearAgo.add(Calendar.MONTH, -monthSpan.intValue());
			//fourMonthsAgo.add(Calendar.MONTH, -4);
			oneYearAgo.add(Calendar.DATE, -(oneYearAgo.get(Calendar.DATE)));
			Calendar endOfMonth = Calendar.getInstance();
			endOfMonth.set(Calendar.DATE, endOfMonth.getMaximum(Calendar.DATE) - 1);
			endOfMonth.set(Calendar.MONTH, endOfMonth.get(Calendar.MONTH));
			endOfMonth.set(Calendar.YEAR, endOfMonth.get(Calendar.YEAR));
			//Calendar today = Calendar.getInstance();
			//today.add(Calendar.DATE, (today.getMaximum(Calendar.DATE) - today.get(Calendar.DATE)));
			//today.set(Calendar.DATE, today.getMaximum(Calendar.DATE), Calendar.MONTH);
			//endOfMonth.add(Calendar.DATE, );
			DateAxis dateAxis = new DateAxis();
			dateAxis.setRange(oneYearAgo.getTime(), endOfMonth.getTime());
			//DateTickUnit dateTickUnit = new DateTickUnit(DateTickUnit.DAY, 15);
			//dateAxis.setTickUnit(dateTickUnit);
			NumberAxis valueAxis = new NumberAxis("Inventory Level (" + packaging + ")");
			//valueAxis.setAutoRangeIncludesZero(false); // override default
			//StandardXYItemRenderer renderer1 = new StandardXYItemRenderer(StandardXYItemRenderer.LINES + StandardXYItemRenderer.SHAPES,ttg, urlg);
			XYLineAndShapeRenderer renderer1 = new XYLineAndShapeRenderer();
			renderer1.setShapesVisible(false);
			renderer1.setShapesFilled(true);
			renderer1.setToolTipGenerator(ttg);
			XYLineAndShapeRenderer renderer3 = new XYLineAndShapeRenderer();
			//renderer3.setSeriesLinesVisible(6, false);
			//renderer3.setSeriesShapesVisible(6, true);
			renderer3.setLinesVisible(false);
			renderer3.setPaint(Color.ORANGE);
			renderer3.setToolTipGenerator(ttg);
			XYLineAndShapeRenderer renderer4 = new XYLineAndShapeRenderer();
			//StandardXYItemRenderer renderer4 = new StandardXYItemRenderer(StandardXYItemRenderer.LINES + StandardXYItemRenderer.SHAPES,ttg, urlg);
			renderer4.setPaint(Color.BLACK);
			renderer4.setShapesVisible(false);
			renderer4.setToolTipGenerator(ttg);
			XYLineAndShapeRenderer renderer5 = new XYLineAndShapeRenderer();
			renderer5.setPaint(Color.CYAN);
			renderer5.setShapesVisible(false);
			renderer5.setToolTipGenerator(ttg);
			XYLineAndShapeRenderer renderer6 = new XYLineAndShapeRenderer();
			renderer6.setPaint(Color.GREEN);
			renderer6.setShapesVisible(false);
			renderer6.setToolTipGenerator(ttg);
			XYLineAndShapeRenderer renderer7 = new XYLineAndShapeRenderer();
			renderer7.setPaint(Color.BLUE);
			renderer7.setShapesVisible(false);
			renderer7.setToolTipGenerator(ttg);
			XYPlot plot = new XYPlot(xyDataset1, dateAxis, valueAxis, renderer1);
			//plot.setDataset(1, xyDataset2);
			//plot.setRenderer(1, renderer2);
			plot.setDataset(3, xyDataset4);
			plot.setRenderer(3, renderer4);
			plot.setDataset(5, xyDataset5);
			plot.setRenderer(5, renderer5);
			plot.setDataset(4, xyDataset6);
			plot.setRenderer(4, renderer6);
			plot.setDataset(2, xyDataset7);
			plot.setRenderer(2, renderer7);
			//plot.setDataset(6, xyDataset3);
			//plot.setRenderer(6, renderer3);
			JFreeChart chart = new JFreeChart("Inventory for " + partNumber + " in " + inventoryGroupName, JFreeChart.DEFAULT_TITLE_FONT, plot, true);
			chart.setBackgroundPaint(java.awt.Color.white);
			//  Write the chart image to the temporary directory
			ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
			//File tempDir = new File(directoryPath + library.getString("chart.image.dir"));
			File tempDir = new File(chartDirectory);
			File file = File.createTempFile("JFREE", ".png", tempDir);
			ChartUtilities.saveChartAsPNG(file, chart, IMAGE_WIDTH, IMAGE_HEIGHT, info);
			filename = file.getName();
			//  Write the image map
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ChartUtilities.writeImageMap(pw, filename, info, false);
			pw.flush();
			this.map = sw.toString();
		} catch (NoDataException e) {
			log.error("Inventory graph query returned no data");
			filename = "public_nodata_500x300.png";
		} catch (Exception e) {
			log.error("Error creating inventory graph.", e);
			filename = "public_error_500x300.png";
		}
		return filename;
	}

	/**
	 * ***************************************************************************
	 * Creates a chart of lead time for the last year using jfreechart.<BR>
	 *
	 * @param inventoryGroup	  inventory group to query by
	 * @param partNumber			part number to query by
	 * @param partGroupNo		  part group number to query by
	 * @param inventoryGroupName inventory group to put in the label
	 * @param catalogId			 catalog id to query by
	 * @returns name of the file that was created
	 * **************************************************************************
	 */
	public String generateLeadtimeChart(String inventoryGroup, String partNumber, String partGroupNo, String inventoryGroupName, String catalogId, String issueGeneration, String catalogCompanyId) {
		String filename = null;
		BigDecimal median = null;
		BigDecimal tenth = null;
		BigDecimal nintieth = null;
		BigDecimal projected = null;
		String leadTimeDataSeriesLabel = null;
		try {
			Collection graphData = this.getLeadtimeData(inventoryGroup, partNumber, partGroupNo, catalogId, catalogCompanyId);
			if (graphData == null || graphData.size() == 0) {
				throw new NoDataException("");
			}
			//  Create and populate an XYSeries Collection
			if (issueGeneration == null) {
				//go get issue generation since Trong doesn't want to get it...
				issueGeneration = this.getIssueGeneration(inventoryGroup);
			}
			if ("Item Counting".equalsIgnoreCase(issueGeneration)) {
				leadTimeDataSeriesLabel = "Receipts";
			} else {
				leadTimeDataSeriesLabel = "Deliveries";
			}
			XYSeries leadtimeDataSeries = new XYSeries(leadTimeDataSeriesLabel);
			XYSeries medianDataSeries = new XYSeries("Median");
			XYSeries tenthDataSeries = new XYSeries("Tenth Percentile");
			XYSeries nintiethDataSeries = new XYSeries("Nintieth Percentile");
			XYSeries projectedDataSeries = new XYSeries("Projected");
			Iterator iterator = graphData.iterator();
			Date minDate = null;
			while (iterator.hasNext()) {
				PartYearlyLeadTimeViewBean bean = (PartYearlyLeadTimeViewBean) iterator.next();
				//leadtimeDataSeries.add(bean.getDateOfReceipt().getTime(), bean.getLeadTime());
				leadtimeDataSeries.add(bean.getXDate().getTime(), bean.getLeadTime());
				median = bean.getMedian();
				tenth = bean.getTenth();
				nintieth = bean.getNintieth();
				projected = bean.getProjectedLeadTime();
				//I need to get the earliest date
				//if (minDate == null || minDate.after(bean.getDateOfReceipt())) {
				if (minDate == null || minDate.after(bean.getXDate())) {
					//minDate = bean.getDateOfReceipt();
					minDate = bean.getXDate();
				}
			}
			//since there aren't records for all dates I'll need to add those manually
			Calendar startingDate = Calendar.getInstance();
			startingDate.add(Calendar.YEAR, -1);
			if (minDate.after(startingDate.getTime())) {
				//there is less than a year worth of data
				startingDate.setTime(minDate);
			}
			Date today = new Date();
			while (today.after(startingDate.getTime())) {
				medianDataSeries.add(startingDate.getTimeInMillis(), median);
				tenthDataSeries.add(startingDate.getTimeInMillis(), tenth);
				nintiethDataSeries.add(startingDate.getTimeInMillis(), nintieth);
				projectedDataSeries.add(startingDate.getTimeInMillis(), projected);
				startingDate.add(Calendar.DATE, 1);
			}
			XYSeriesCollection xyDataset1 = new XYSeriesCollection();
			xyDataset1.addSeries(leadtimeDataSeries);
			XYSeriesCollection xyDataset2 = new XYSeriesCollection();
			xyDataset2.addSeries(tenthDataSeries);
			xyDataset2.addSeries(medianDataSeries);
			xyDataset2.addSeries(nintiethDataSeries);
			xyDataset2.addSeries(projectedDataSeries);
			//  Create tooltip and URL generators
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
			StandardXYToolTipGenerator ttg = new StandardXYToolTipGenerator(StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT, sdf, NumberFormat.getInstance());
			TimeSeriesURLGenerator urlg = null;
			//TimeSeriesURLGenerator urlg = new TimeSeriesURLGenerator(sdf, "pie_chart.jsp", "series", "hitDate");
			//  Create the chart object
			Calendar oneYearAgo = Calendar.getInstance();
			oneYearAgo.add(Calendar.YEAR, -1);
			oneYearAgo.add(Calendar.DATE, -(oneYearAgo.get(Calendar.DATE)));
			Calendar endOfMonth = Calendar.getInstance();
			endOfMonth.set(Calendar.DATE, endOfMonth.getMaximum(Calendar.DATE) - 1);
			endOfMonth.set(Calendar.MONTH, endOfMonth.get(Calendar.MONTH));
			endOfMonth.set(Calendar.YEAR, endOfMonth.get(Calendar.YEAR));
			DateAxis dateAxis = new DateAxis();
			dateAxis.setRange(oneYearAgo.getTime(), endOfMonth.getTime());
			NumberAxis valueAxis = new NumberAxis("Lead Time (days)");
			//valueAxis.setAutoRangeIncludesZero(false); // override default
			XYLineAndShapeRenderer renderer1 = new XYLineAndShapeRenderer();
			renderer1.setLinesVisible(false);
			renderer1.setPaint(Color.ORANGE);
			renderer1.setToolTipGenerator(ttg);
			StandardXYItemRenderer renderer2 = new StandardXYItemRenderer(StandardXYItemRenderer.LINES + StandardXYItemRenderer.SHAPES, ttg, urlg);

//not in the new version
//      renderer2.setDefaultShapesVisible(false);
			XYPlot plot = new XYPlot(xyDataset2, dateAxis, valueAxis, renderer2);
			plot.setDataset(1, xyDataset1);
			plot.setRenderer(1, renderer1);
			JFreeChart chart = new JFreeChart("Lead Time for " + partNumber + " in " + inventoryGroupName, JFreeChart.DEFAULT_TITLE_FONT, plot, true);
			chart.setBackgroundPaint(java.awt.Color.white);
			//  Write the chart image to the temporary directory
			ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
			//File tempDir = new File(directoryPath + library.getString("chart.image.dir"));
			File tempDir = new File(chartDirectory);
			File file = File.createTempFile("JFREE", ".png", tempDir);
			ChartUtilities.saveChartAsPNG(file, chart, IMAGE_WIDTH, IMAGE_HEIGHT, info);
			filename = file.getName();
			//  Write the image map
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ChartUtilities.writeImageMap(pw, filename, info, false);
			pw.flush();
			this.map = sw.toString();
		} catch (NoDataException e) {
			log.error("Lead time chart returned no data.");
			filename = "public_nodata_500x300.png";
		} catch (Exception e) {
			log.error("Error creating lead time chart.", e);
			filename = "public_error_500x300.png";
		}
		return filename;
	}

	/**
	 * ***************************************************************************
	 * Creates a chart of lead time for the last year using jfreechart.<BR>
	 *
	 * @param inventoryGroup	  inventory group to query by
	 * @param partNumber			part number to query by
	 * @param partGroupNo		  part group number to query by
	 * @param inventoryGroupName inventory group to put in the label
	 * @param catalogId			 catalog id to query by
	 * @returns name of the file that was created
	 * **************************************************************************
	 */
	public String generateMrLeadtimeChart(String inventoryGroup, String partNumber, String partGroupNo, String inventoryGroupName, String catalogId, String issueGeneration, String catalogCompanyId) {
		String filename = null;
		BigDecimal median = null;
		BigDecimal tenth = null;
		BigDecimal nintieth = null;
		String leadTimeDataSeriesLabel = null;
		try {
			Collection graphData = this.getMrLeadtimeData(inventoryGroup, partNumber, partGroupNo, catalogId, catalogCompanyId);
			if (graphData == null || graphData.size() == 0) {
				throw new NoDataException("");
			}
			//  Create and populate an XYSeries Collection
			if (issueGeneration == null) {
				//go get issue generation since Trong doesn't want to get it...
				issueGeneration = this.getIssueGeneration(inventoryGroup);
			}
			if ("Item Counting".equalsIgnoreCase(issueGeneration)) {
				leadTimeDataSeriesLabel = "Material Request";
			} else {
				leadTimeDataSeriesLabel = "Deliveries";
			}
			XYSeries leadtimeDataSeries = new XYSeries(leadTimeDataSeriesLabel);
			XYSeries medianDataSeries = new XYSeries("Median");
			XYSeries tenthDataSeries = new XYSeries("Tenth Percentile");
			XYSeries nintiethDataSeries = new XYSeries("Nintieth Percentile");
			Iterator iterator = graphData.iterator();
			Date minDate = null;
			while (iterator.hasNext()) {
				PartMrYearlyLeadTimeViewBean bean = (PartMrYearlyLeadTimeViewBean) iterator.next();
				//leadtimeDataSeries.add(bean.getDateOfReceipt().getTime(), bean.getLeadTime());
				leadtimeDataSeries.add(bean.getXDate().getTime(), bean.getLeadTime());
				median = bean.getMedian();
				tenth = bean.getTenth();
				nintieth = bean.getNintieth();
				//I need to get the earliest date
				//if (minDate == null || minDate.after(bean.getDateOfReceipt())) {
				if (minDate == null || minDate.after(bean.getXDate())) {
					//minDate = bean.getDateOfReceipt();
					minDate = bean.getXDate();
				}
			}
			//since there aren't records for all dates I'll need to add those manually
			Calendar startingDate = Calendar.getInstance();
			startingDate.add(Calendar.YEAR, -1);
			if (minDate.after(startingDate.getTime())) {
				//there is less than a year worth of data
				startingDate.setTime(minDate);
			}
			Date today = new Date();
			while (today.after(startingDate.getTime())) {
				medianDataSeries.add(startingDate.getTimeInMillis(), median);
				tenthDataSeries.add(startingDate.getTimeInMillis(), tenth);
				nintiethDataSeries.add(startingDate.getTimeInMillis(), nintieth);
				startingDate.add(Calendar.DATE, 1);
			}
			XYSeriesCollection xyDataset1 = new XYSeriesCollection();
			xyDataset1.addSeries(leadtimeDataSeries);
			XYSeriesCollection xyDataset2 = new XYSeriesCollection();
			xyDataset2.addSeries(tenthDataSeries);
			xyDataset2.addSeries(medianDataSeries);
			xyDataset2.addSeries(nintiethDataSeries);
			//  Create tooltip and URL generators
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
			StandardXYToolTipGenerator ttg = new StandardXYToolTipGenerator(StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT, sdf, NumberFormat.getInstance());
			TimeSeriesURLGenerator urlg = null;
			//TimeSeriesURLGenerator urlg = new TimeSeriesURLGenerator(sdf, "pie_chart.jsp", "series", "hitDate");
			//  Create the chart object
			Calendar oneYearAgo = Calendar.getInstance();
			oneYearAgo.add(Calendar.YEAR, -1);
			oneYearAgo.add(Calendar.DATE, -(oneYearAgo.get(Calendar.DATE)));
			Calendar endOfMonth = Calendar.getInstance();
			endOfMonth.set(Calendar.DATE, endOfMonth.getMaximum(Calendar.DATE) - 1);
			endOfMonth.set(Calendar.MONTH, endOfMonth.get(Calendar.MONTH));
			endOfMonth.set(Calendar.YEAR, endOfMonth.get(Calendar.YEAR));
			DateAxis dateAxis = new DateAxis();
			dateAxis.setRange(oneYearAgo.getTime(), endOfMonth.getTime());
			NumberAxis valueAxis = new NumberAxis("Lead Time (days)");
			//valueAxis.setAutoRangeIncludesZero(false); // override default
			XYLineAndShapeRenderer renderer1 = new XYLineAndShapeRenderer();
			renderer1.setLinesVisible(false);
			renderer1.setPaint(Color.ORANGE);
			renderer1.setToolTipGenerator(ttg);
			XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer();
			renderer2.setShapesVisible(false);
//not in the new version
//      renderer2.setDefaultShapesVisible(false);
			XYPlot plot = new XYPlot(xyDataset2, dateAxis, valueAxis, renderer2);
			plot.setDataset(1, xyDataset1);
			plot.setRenderer(1, renderer1);
			JFreeChart chart = new JFreeChart("MR Lead Time for " + partNumber + " in " + inventoryGroupName, JFreeChart.DEFAULT_TITLE_FONT, plot, true);
			chart.setBackgroundPaint(java.awt.Color.white);
			//  Write the chart image to the temporary directory
			ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
			//File tempDir = new File(directoryPath + library.getString("chart.image.dir"));
			File tempDir = new File(chartDirectory);
			File file = File.createTempFile("JFREE", ".png", tempDir);
			ChartUtilities.saveChartAsPNG(file, chart, IMAGE_WIDTH, IMAGE_HEIGHT, info);
			filename = file.getName();
			//  Write the image map
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ChartUtilities.writeImageMap(pw, filename, info, false);
			pw.flush();
			this.map = sw.toString();
		} catch (NoDataException e) {
			log.error("Lead time chart returned no data.");
			filename = "public_nodata_500x300.png";
		} catch (Exception e) {
			log.error("Error creating lead time chart.", e);
			filename = "public_error_500x300.png";
		}
		return filename;
	}

	/**
	 * ***************************************************************************
	 * Creates a chart of lead time for the last year using jfreechart.<BR>
	 *
	 * @param inventoryGroup	  inventory group to query by
	 * @param partNumber			part number to query by
	 * @param partGroupNo		  part group number to query by
	 * @param inventoryGroupName inventory group to put in the label
	 * @param catalogId			 catalog id to query by
	 * @returns name of the file that was created
	 * **************************************************************************
	 */
	public String generateSupplyLeadtimeChart(String inventoryGroup, String partNumber, String partGroupNo, String inventoryGroupName, String catalogId, String issueGeneration, String catalogCompanyId) {
		String filename = null;
		BigDecimal median = null;
		BigDecimal tenth = null;
		BigDecimal nintieth = null;
		String leadTimeDataSeriesLabel = null;
		try {
			Collection graphData = this.getSupplyLeadtimeData(inventoryGroup, partNumber, partGroupNo, catalogId,catalogCompanyId);
			if (graphData == null || graphData.size() == 0) {
				throw new NoDataException("");
			}
			//  Create and populate an XYSeries Collection
			if (issueGeneration == null) {
				//go get issue generation since Trong doesn't want to get it...
				issueGeneration = this.getIssueGeneration(inventoryGroup);
			}
			if ("Item Counting".equalsIgnoreCase(issueGeneration)) {
				leadTimeDataSeriesLabel = "Receipt";
			} else {
				leadTimeDataSeriesLabel = "Delivery";
			}
			XYSeries leadtimeDataSeries = new XYSeries(leadTimeDataSeriesLabel);
			XYSeries medianDataSeries = new XYSeries("Median");
			XYSeries tenthDataSeries = new XYSeries("Tenth Percentile");
			XYSeries nintiethDataSeries = new XYSeries("Nintieth Percentile");
			Iterator iterator = graphData.iterator();
			Date minDate = null;
			while (iterator.hasNext()) {
				PartSupYearlyLeadTimeViewBean bean = (PartSupYearlyLeadTimeViewBean) iterator.next();
				//leadtimeDataSeries.add(bean.getDateOfReceipt().getTime(), bean.getLeadTime());
				leadtimeDataSeries.add(bean.getXDate().getTime(), bean.getLeadTime());
				median = bean.getMedian();
				tenth = bean.getTenth();
				nintieth = bean.getNintieth();
				//I need to get the earliest date
				//if (minDate == null || minDate.after(bean.getDateOfReceipt())) {
				if (minDate == null || minDate.after(bean.getXDate())) {
					//minDate = bean.getDateOfReceipt();
					minDate = bean.getXDate();
				}
			}
			//since there aren't records for all dates I'll need to add those manually
			Calendar startingDate = Calendar.getInstance();
			startingDate.add(Calendar.YEAR, -1);
			if (minDate.after(startingDate.getTime())) {
				//there is less than a year worth of data
				startingDate.setTime(minDate);
			}
			Date today = new Date();
			while (today.after(startingDate.getTime())) {
				medianDataSeries.add(startingDate.getTimeInMillis(), median);
				tenthDataSeries.add(startingDate.getTimeInMillis(), tenth);
				nintiethDataSeries.add(startingDate.getTimeInMillis(), nintieth);
				startingDate.add(Calendar.DATE, 10);
			}
			XYSeriesCollection xyDataset1 = new XYSeriesCollection();
			xyDataset1.addSeries(leadtimeDataSeries);
			XYSeriesCollection xyDataset2 = new XYSeriesCollection();
			xyDataset2.addSeries(tenthDataSeries);
			xyDataset2.addSeries(medianDataSeries);
			xyDataset2.addSeries(nintiethDataSeries);

			//  Create tooltip and URL generators
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
			StandardXYToolTipGenerator ttg = new StandardXYToolTipGenerator(StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT, sdf, NumberFormat.getInstance());
			TimeSeriesURLGenerator urlg = null;
			//TimeSeriesURLGenerator urlg = new TimeSeriesURLGenerator(sdf, "pie_chart.jsp", "series", "hitDate");
			//  Create the chart object
			Calendar oneYearAgo = Calendar.getInstance();
			oneYearAgo.add(Calendar.YEAR, -1);
			oneYearAgo.add(Calendar.DATE, -(oneYearAgo.get(Calendar.DATE)));
			Calendar endOfMonth = Calendar.getInstance();
			endOfMonth.set(Calendar.DATE, endOfMonth.getMaximum(Calendar.DATE) - 1);
			endOfMonth.set(Calendar.MONTH, endOfMonth.get(Calendar.MONTH));
			endOfMonth.set(Calendar.YEAR, endOfMonth.get(Calendar.YEAR));
			DateAxis dateAxis = new DateAxis();
			dateAxis.setRange(oneYearAgo.getTime(), endOfMonth.getTime());
			NumberAxis valueAxis = new NumberAxis("Lead Time (days)");
			//valueAxis.setAutoRangeIncludesZero(false); // override default
			XYLineAndShapeRenderer renderer1 = new XYLineAndShapeRenderer();
			//renderer1.setLinesVisible(false);
			renderer1.setPaint(Color.ORANGE);
			renderer1.setToolTipGenerator(ttg);
			XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer();
			renderer2.setShapesVisible(false);

//not in the new version
//      renderer2.setDefaultShapesVisible(false);
			XYPlot plot = new XYPlot(xyDataset2, dateAxis, valueAxis, renderer2);
			plot.setDataset(1, xyDataset1);
			plot.setRenderer(1, renderer1);
			JFreeChart chart = new JFreeChart("Supply Lead Time for " + partNumber + " in " + inventoryGroupName, JFreeChart.DEFAULT_TITLE_FONT, plot, true);
			chart.setBackgroundPaint(java.awt.Color.white);
			//  Write the chart image to the temporary directory
			ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
			//File tempDir = new File(directoryPath + library.getString("chart.image.dir"));
			File tempDir = new File(chartDirectory);
			File file = File.createTempFile("JFREE", ".png", tempDir);
			ChartUtilities.saveChartAsPNG(file, chart, IMAGE_WIDTH, IMAGE_HEIGHT, info);
			filename = file.getName();
			//  Write the image map
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ChartUtilities.writeImageMap(pw, filename, info, false);
			pw.flush();
			this.map = sw.toString();
		} catch (NoDataException e) {
			log.error("Lead time chart returned no data.");
			filename = "public_nodata_500x300.png";
		} catch (Exception e) {
			log.error("Error creating lead time chart.", e);
			filename = "public_error_500x300.png";
		}
		return filename;
	}

	/**
	 * ***************************************************************************
	 * Creates a chart of receipts for the last year using jfreechart.<BR>
	 *
	 * @param inventoryGroup	  inventory group to query by
	 * @param partNumber			part number to query by
	 * @param partGroupNo		  part group number to query by
	 * @param inventoryGroupName inventory group to put in the label
	 * @param catalogId			 catalog id to query by
	 * @returns names of the files that was created
	 * **************************************************************************
	 */
	public Collection generateChemicalDashboardChart() {
		//Collection fileNames = new Vector();
		//String filename = null;
		//String packaging = null;
		Collection graphData = null;
		try {
			graphData = this.getDashboardDataData();
			if (graphData == null || graphData.size() == 0) {
				throw new NoDataException("");
			}
			Iterator iterator = graphData.iterator();

			while (iterator.hasNext()) {
				IgCountInventoryViewBean bean = (IgCountInventoryViewBean) iterator.
					 next();
				bean.setFileName(this.getDashboardFileName(bean));
				//fileNames.add(this.getDashboardFileName(bean));
			}

		} catch (Exception e) {
			log.error("Error creating receipt chart.", e);
			//filename = "public_error_500x300.png";
		}
		return graphData;
	}

	private String getDashboardFileName(IgCountInventoryViewBean bean) throws BaseException {
		String filename = null;
		String packaging = null;
		try {

			String tankName = "";
			String lastCountDate = "";
			String nextDeliveryDate = "";
			String currentLevel = "";
			String lowAlarmLevel = "";
			String highAlarmLevel = "";
			String currentLevelPercent = "";
			String lowAlarmLevelPercent = "";
			String highAlarmLevelPercent = "";
			BigDecimal[][] data = new BigDecimal[][]{{new BigDecimal("1")}, {new BigDecimal("50")}};
//log.debug("level:" + bean.getInventoryFraction());
			data[0][0] = bean.getInventoryFraction().multiply(new BigDecimal("100"));
			data[1][0] = (new BigDecimal("1").subtract(bean.getInventoryFraction())).
				 multiply(new BigDecimal("100"));
			//dataset.addValue(bean.getInventory(),"","");
			tankName = bean.getTankName();
			if (bean.getLastCountDate() != null) {
				lastCountDate = DateHandler.formatOracleDate(bean.getLastCountDate());
			}
			if (bean.getNextDeliveryDate() != null) {
				nextDeliveryDate = DateHandler.formatOracleDate(bean.
					 getNextDeliveryDate());
			}
			if (bean.getInventoryFraction() != null) {
				currentLevel = (bean.getInventory().setScale(0, BigDecimal.ROUND_HALF_UP)).intValue() + " " + bean.getInventoryUom();
				currentLevelPercent = "" + (bean.getInventoryCountUom().setScale(0, BigDecimal.ROUND_HALF_UP)).intValue();
//log.debug("d:" + bean.getInventoryCountUom());
			}
			if (bean.getHighAlarmFraction() != null) {
				highAlarmLevel = (bean.getHighAlarm().setScale(0, BigDecimal.ROUND_HALF_UP)).intValue() + " " + bean.getInventoryUom();
				highAlarmLevelPercent = "" + (bean.getHighAlarmCountUom().setScale(0, BigDecimal.ROUND_HALF_UP)).intValue();
			}
			if (bean.getLowAlarmFraction() != null) {
				lowAlarmLevel = (bean.getLowAlarm().setScale(0, BigDecimal.ROUND_HALF_UP)).intValue() + " " + bean.getInventoryUom();
				lowAlarmLevelPercent = "" + (bean.getLowAlarmCountUom().setScale(0, BigDecimal.ROUND_HALF_UP)).intValue();
			}


			final CategoryDataset dataset = DatasetUtilities.createCategoryDataset("", "", data);
			JFreeChart chart = ChartFactory.createStackedBarChart(tankName, // chart title
				 " ", // domain axis label
				 "", // range axis label
				 dataset, // data
				 PlotOrientation.VERTICAL, // the plot orientation
				 false, // legend
				 true, // tooltips
				 false // urls
			);

			Stroke alarmStroke = new BasicStroke(1.0f,	 // Width
				 BasicStroke.CAP_SQUARE,	 // End cap
				 BasicStroke.JOIN_ROUND,	 // Join style
				 10.0f,							// Miter limit
				 new float[]{7.0f, 7.0f}, // Dash pattern
				 0.0f);

			Font myFont = new Font("SansSerif", Font.BOLD, 10);
			chart.getTitle().setFont(myFont);
			Font subFont = new Font("SansSerif", Font.PLAIN, 9);
			TextTitle subTitle1 = new TextTitle("Last read: " + lastCountDate);
			subTitle1.setFont(subFont);
			TextTitle subTitle2 = new TextTitle("Next delivery: " + nextDeliveryDate);
			subTitle2.setFont(subFont);
			TextTitle subTitle3 = new TextTitle("Current: " + currentLevel + " (" + currentLevelPercent + " " + bean.getCountUom() + ")");
			subTitle3.setFont(subFont);
			TextTitle subTitle4 = new TextTitle("High Alarm: " + highAlarmLevel + " (" + highAlarmLevelPercent + " " + bean.getCountUom() + ")");
			subTitle4.setFont(subFont);
			TextTitle subTitle5 = new TextTitle("Low Alarm: " + lowAlarmLevel + " (" + lowAlarmLevelPercent + " " + bean.getCountUom() + ")");
			subTitle5.setFont(subFont);
			chart.addSubtitle(subTitle1);
			chart.addSubtitle(subTitle2);
			chart.addSubtitle(subTitle3);
			if (bean.getHighAlarmFraction() == null) {
				subTitle4.setText("High Alarm: None");
			}
			if (bean.getLowAlarmFraction() == null) {
				subTitle5.setText("Low Alarm: None");
			}
			chart.addSubtitle(subTitle4);
			chart.addSubtitle(subTitle5);
			chart.setBackgroundPaint(Color.lightGray);
			CategoryPlot plot = (CategoryPlot) chart.getPlot();
			plot.setRangeGridlinesVisible(false);
			plot.setBackgroundPaint(Color.lightGray);
			//if alarm level is defined I'll display it
			if (bean.getLowAlarm() != null) {
				IntervalMarker target = new IntervalMarker((bean.getLowAlarmFraction()).multiply(new BigDecimal("100")).doubleValue(), (bean.getLowAlarmFraction()).multiply(new BigDecimal("100")).doubleValue() + 0.5);

				target.setPaint(Color.red);
				target.setOutlinePaint(Color.red);
				target.setOutlineStroke(alarmStroke);
				plot.addRangeMarker(target, Layer.FOREGROUND);
			}
			if (bean.getHighAlarm() != null) {
				IntervalMarker target = new IntervalMarker((bean.getHighAlarmFraction()).multiply(new BigDecimal("100")).doubleValue(), (bean.getHighAlarmFraction()).multiply(new BigDecimal("100")).doubleValue() + 0.5);

				target.setPaint(Color.red);
				target.setOutlinePaint(Color.red);
				target.setOutlineStroke(alarmStroke);
				plot.addRangeMarker(target, Layer.FOREGROUND);
			}
			if (bean.getTankDivisions() != null) {
				BigDecimal interval = new BigDecimal("100").divide(bean.getTankDivisions(), 5, BigDecimal.ROUND_HALF_UP);
				double max = 99;
				double counter = interval.doubleValue();
				IntervalMarker line = null;
				while (counter < max) {
					line = new IntervalMarker(counter, counter + 1);
					line.setPaint(Color.white);
					plot.addRangeMarker(line, Layer.FOREGROUND);
					counter = counter + interval.doubleValue();
				}
			}

			StackedBarRenderer renderer = (StackedBarRenderer) plot.getRenderer();
			//if the level is below low level alarm make the color yellow
			if (bean.getLowAlarmFraction() != null && (bean.getInventoryFraction().compareTo(bean.getLowAlarmFraction()) < 0)) {
				renderer.setSeriesPaint(0, Color.yellow);
			} else {
				renderer.setSeriesPaint(0, Color.blue);
			}
			renderer.setSeriesPaint(1, Color.cyan);
			renderer.setDrawBarOutline(false);
			renderer.setItemMargin(0);
			//renderer.setMaximumBarWidth(1);
			renderer.setMinimumBarLength(1);

			final CategoryAxis domainAxis = plot.getDomainAxis();
//        domainAxis.setCategoryMargin(0.25);
//        domainAxis.setUpperMargin(0.05);
//        domainAxis.setLowerMargin(0.05);
			domainAxis.setVisible(false);
			//domainAxis.setCategoryMargin(0);
			//domainAxis.setTickMarkOutsideLength(0);
			NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
			rangeAxis.setRange(0.0, 100.0);
			rangeAxis.setVisible(false);

			ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
			File tempDir = new File(chartDirectory);
			File file = File.createTempFile("JFREE", ".png", tempDir);
			ChartUtilities.saveChartAsPNG(file, chart, DASHBOARD_IMAGE_WIDTH, DASHBOARD_IMAGE_HEIGHT, info);

			filename = file.getName();
			//  Write the image map to the PrintWriter
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ChartUtilities.writeImageMap(pw, filename, info, false);
			pw.flush();
			//this.map = sw.toString();
		}
//    catch (NoDataException e) {
//      log.error("Receipts chart returned no data.");
//      filename = "public_nodata_500x300.png";
//    }
		catch (Exception e) {
			log.error("Error creating receipt chart.", e);
			filename = "public_error_500x300.png";
		}
		return filename;
	}

	private Collection getDashboardDataData() throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		IgCountInventoryViewBeanFactory factory = new IgCountInventoryViewBeanFactory(dbManager);
		SortCriteria sortCriteria = new SortCriteria();
		sortCriteria.addCriterion("inventoryGroupCollection");
		sortCriteria.addCriterion("tankName");
		return factory.select(new SearchCriteria(), sortCriteria);
	}

	private Collection getIssuesData(String inventoryGroup, String partNumber, String partGroupNo, String catalogId, String catalogCompanyId) throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		DailyPartIssueSumViewBeanFactory factory = new DailyPartIssueSumViewBeanFactory(dbManager);
		Date today = new Date();
		Calendar fourMonthsAgo = Calendar.getInstance();
		fourMonthsAgo.set(fourMonthsAgo.get(Calendar.YEAR) - 1, fourMonthsAgo.get(Calendar.MONTH), 1);
		SearchCriteria criteria = new SearchCriteria();
		if (!StringHandler.isBlankString(catalogCompanyId)) {
			criteria.addCriterion("catalogCompanyId", SearchCriterion.EQUALS, catalogCompanyId);
		}
		criteria.addCriterion("catalogId", SearchCriterion.EQUALS, catalogId);
		criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, inventoryGroup);
		criteria.addCriterion("catPartNo", SearchCriterion.EQUALS, partNumber);
		criteria.addCriterion("partGroupNo", SearchCriterion.EQUALS, partGroupNo);
		criteria.addCriterion("dailyDate", SearchCriterion.GREATER_THAN_OR_EQUAL_TO, DateHandler.formatDate(fourMonthsAgo.getTime(), "MM/dd/yyyy"));
		criteria.addCriterion("dailyDate", SearchCriterion.LESS_THAN_OR_EQUAL_TO, DateHandler.formatDate(new Date(), "MM/dd/yyyy"));
		return factory.select(criteria);
	}

	private Collection getReceiptData(String inventoryGroup, String partNumber, String partGroupNo, String catalogId, String catalogCompanyId) throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		DailyPartReceiptSumViewBeanFactory factory = new DailyPartReceiptSumViewBeanFactory(dbManager);
		Date today = new Date();
		Calendar oneYearAgo = Calendar.getInstance();
		oneYearAgo.set(oneYearAgo.get(Calendar.YEAR) - 1, oneYearAgo.get(Calendar.MONTH), 1);
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("quantity", SearchCriterion.IS_NOT, "null");
		if (!StringHandler.isBlankString(catalogCompanyId)) {
			criteria.addCriterion("catalogCompanyId", SearchCriterion.EQUALS, catalogCompanyId);
		}
		criteria.addCriterion("catalogId", SearchCriterion.EQUALS, catalogId);
		criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, inventoryGroup);
		criteria.addCriterion("catPartNo", SearchCriterion.EQUALS, partNumber);
		criteria.addCriterion("partGroupNo", SearchCriterion.EQUALS, partGroupNo);
		criteria.addCriterion("dailyDate", SearchCriterion.GREATER_THAN_OR_EQUAL_TO, DateHandler.formatDate(oneYearAgo.getTime(), "MM/dd/yyyy"));
		criteria.addCriterion("dailyDate", SearchCriterion.LESS_THAN_OR_EQUAL_TO, DateHandler.formatDate(new Date(), "MM/dd/yyyy"));
		return factory.select(criteria);
	}

	private Collection getInventoryData(String inventoryGroup, String partNumber, String partGroupNo, String catalogId, BigDecimal monthSpan, String catalogCompanyId) throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		DailyPartInventorySumViewBeanFactory factory = new DailyPartInventorySumViewBeanFactory(dbManager);
		Date today = new Date();
		Calendar startMonth = Calendar.getInstance();
		startMonth.set(startMonth.get(Calendar.YEAR), startMonth.get(Calendar.MONTH) - monthSpan.intValue(), 1, 0, 0, 0);

		return factory.selectInventory(inventoryGroup, partNumber, partGroupNo, catalogId, startMonth.getTime(), new Date(),catalogCompanyId);
	}

	private Collection getLeadtimeData(String inventoryGroup, String partNumber, String partGroupNo, String catalogId, String catalogCompanyId) throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		PartYearlyLeadTimeViewBeanFactory factory = new PartYearlyLeadTimeViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		if (!StringHandler.isBlankString(catalogCompanyId)) {
			criteria.addCriterion("catalogCompanyId", SearchCriterion.EQUALS, catalogCompanyId);
		}
		criteria.addCriterion("catalogId", SearchCriterion.EQUALS, catalogId);
		criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, inventoryGroup);
		criteria.addCriterion("catPartNo", SearchCriterion.EQUALS, partNumber);
		criteria.addCriterion("partGroupNo", SearchCriterion.EQUALS, partGroupNo);
		return factory.select(criteria);
	}

	private Collection getMrLeadtimeData(String inventoryGroup, String partNumber, String partGroupNo, String catalogId, String catalogCompanyId) throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		PartMrYearlyLeadTimeViewBeanFactory factory = new PartMrYearlyLeadTimeViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		if (!StringHandler.isBlankString(catalogCompanyId)) {
			criteria.addCriterion("catalogCompanyId", SearchCriterion.EQUALS, catalogCompanyId);
		}
		criteria.addCriterion("catalogId", SearchCriterion.EQUALS, catalogId);
		criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, inventoryGroup);
		criteria.addCriterion("catPartNo", SearchCriterion.EQUALS, partNumber);
		criteria.addCriterion("partGroupNo", SearchCriterion.EQUALS, partGroupNo);
		return factory.select(criteria);
	}

	private Collection getSupplyLeadtimeData(String inventoryGroup, String partNumber, String partGroupNo, String catalogId, String catalogCompanyId) throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		PartSupYearlyLeadTimeViewBeanFactory factory = new PartSupYearlyLeadTimeViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		if (!StringHandler.isBlankString(catalogCompanyId)) {
			criteria.addCriterion("catalogCompanyId", SearchCriterion.EQUALS, catalogCompanyId);
		}
		criteria.addCriterion("catalogId", SearchCriterion.EQUALS, catalogId);
		criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, inventoryGroup);
		criteria.addCriterion("catPartNo", SearchCriterion.EQUALS, partNumber);
		criteria.addCriterion("partGroupNo", SearchCriterion.EQUALS, partGroupNo);
		return factory.select(criteria);
	}

	private String getIssueGeneration(String inventoryGroup) throws BaseException {
		String issueGeneration = null;
		DbManager dbManager = new DbManager(getClient());
		InventoryGroupDefinitionBeanFactory fac = new InventoryGroupDefinitionBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, inventoryGroup);
		Collection c = fac.select(criteria);
		Iterator iterator = c.iterator();
		while (iterator.hasNext()) {
			InventoryGroupDefinitionBean bean = (InventoryGroupDefinitionBean) iterator.next();
			issueGeneration = bean.getIssueGeneration();
		}
		return issueGeneration;
	}
}
