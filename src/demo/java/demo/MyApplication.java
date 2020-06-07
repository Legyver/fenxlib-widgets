package demo;

import com.legyver.fenxlib.config.options.ApplicationOptions;
import com.legyver.fenxlib.config.options.DefaultApplicationOptions;
import com.legyver.fenxlib.factory.*;
import com.legyver.fenxlib.factory.menu.*;
import com.legyver.fenxlib.factory.options.BorderPaneInitializationOptions;
import com.legyver.fenxlib.locator.query.ComponentQuery;
import com.legyver.fenxlib.locator.query.QueryableComponentRegistry;
import com.legyver.fenxlib.uimodel.IUiModel;
import com.legyver.fenxlib.util.GuiUtil;
import com.legyver.fenxlib.widget.about.AboutMenuItemFactory;
import com.legyver.fenxlib.widget.about.AboutPageOptions;
import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.function.Supplier;

public class MyApplication extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		AboutPageOptions aboutPageOptions = new AboutPageOptions.Builder(getClass())
				.dependenciesFile("license.properties")
				.buildPropertiesFile("build.properties")
				.copyrightPropertiesFile("copyright.properties")
				.title("MyApplication")
				.intro("MyApplication makes amazing things easy")
				.gist("More stuff about how great this app is.  I can go on about it for a really long time and the text will wrap around.")
				.additionalInfo("be sure to tell your friends")
				.build();

		ApplicationOptions applicationOptions = new MyApplicationOptions(primaryStage, new ApplicationUIModel());
		GuiUtil.init(applicationOptions);
		SceneFactory sceneFactory = new SceneFactory(primaryStage, 600, 650, MyApplication.class.getResource("application.css"));

		//query for the common registry the application components register with
		Supplier<QueryableComponentRegistry> registrySupplier = () -> (QueryableComponentRegistry) applicationOptions.getComponentRegistry();

		//where to display the popup over
		Supplier<StackPane> centerContentReference = () -> {
			Optional<StackPane> center = new ComponentQuery.QueryBuilder(registrySupplier.get())
					.inRegion(BorderPaneInitializationOptions.REGION_CENTER)
					.type(StackPane.class).execute();
			return center.get();
		};

		BorderPaneInitializationOptions options = new BorderPaneInitializationOptions.Builder()
				.center()
				//popup will display over this. See the centerContentReference Supplier above
				.factory(new StackPaneRegionFactory(true, new TextFactory("Hello World")))
				.up().top()
				.factory(new TopRegionFactory(
						new LeftMenuOptions(
								new MenuFactory("File",
										new ExitMenuItemFactory("Exit")
								)
						),
						new CenterOptions(new TextFieldFactory(false)),
						new RightMenuOptions(
								new MenuFactory("Help", new AboutMenuItemFactory("About", centerContentReference, aboutPageOptions))
						))
				).up().build();

		BorderPane root = new BorderPaneFactory(options).makeBorderPane();
		primaryStage.setScene(sceneFactory.makeScene(root));
		primaryStage.setTitle("About Page Demo");
		primaryStage.show();
	}

	private static class MyApplicationOptions extends DefaultApplicationOptions<ApplicationUIModel> {

		public MyApplicationOptions(Stage primaryStage, ApplicationUIModel uiModel) {
			super("FenxlibWidgetsDemo", primaryStage, uiModel);
		}
	}

	private static class ApplicationUIModel implements IUiModel {

	}
}
