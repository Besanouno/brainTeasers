package pl.polpress.pdf;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.eclipse.birt.report.engine.api.RenderOption;

public class PuzzlePrinter {
	public boolean print() {
		try {
			EngineConfig config = prepareConfig();
			Platform.startup( config );
			IReportEngineFactory factory = (IReportEngineFactory) Platform
			.createFactoryObject( IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY );
			IReportEngine engine = factory.createReportEngine( config );		

			IReportRunnable design = engine.openReportDesign("src/main/resources/puzzle.rptdesign"); 
			IRunAndRenderTask task = engine.createRunAndRenderTask(design); 	
			task.validateParameters();

			RenderOption options = new PDFRenderOption();		
			options.setOutputFileName("output/report.pdf");
			options.setOutputFormat("pdf");
/*
			RenderOption options = new HTMLRenderOption();
			options.setOutputFileName("output/report.html");
			options.setOutputFormat("html");
	*/	
			task.setRenderOption(options);
			task.run();
			task.close();
			engine.destroy();
		} catch (BirtException e) {
			e.printStackTrace();
			return false;
		} finally {
			Platform.shutdown();
		}return true;
	}

	private EngineConfig prepareConfig() {
		EngineConfig config = new EngineConfig( );			
		config.setLogConfig("/tmp/logs", java.util.logging.Level.FINEST);
		return config;
	}
}
