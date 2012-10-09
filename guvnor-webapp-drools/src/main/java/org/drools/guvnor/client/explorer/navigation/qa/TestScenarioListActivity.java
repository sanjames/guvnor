package org.drools.guvnor.client.explorer.navigation.qa;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import org.drools.guvnor.client.common.GenericCallback;
import org.drools.guvnor.client.explorer.ClientFactory;
import org.drools.guvnor.client.messages.Constants;
import org.drools.guvnor.client.rpc.Module;
import org.uberfire.client.annotations.OnStart;
import org.uberfire.client.annotations.WorkbenchPartTitle;
import org.uberfire.client.annotations.WorkbenchPartView;
import org.uberfire.client.annotations.WorkbenchScreen;
import org.uberfire.shared.mvp.PlaceRequest;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
@WorkbenchScreen(identifier = "testScenarioList")
public class TestScenarioListActivity {

    private final SimplePanel simplePanel = new SimplePanel();

    private final ClientFactory clientFactory;
    private String moduleUuid;

    @Inject
    public TestScenarioListActivity(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @OnStart
    public void init(final PlaceRequest place) {
        moduleUuid = place.getParameters().get("moduleUuid");
    }


    @WorkbenchPartTitle
    public String getTitle() {
        return Constants.INSTANCE.ScenariosForPackage("packageConfigData.getName()");
    }

    @WorkbenchPartView
    public Widget asWidget() {

        clientFactory.getModuleService().loadModule(
                moduleUuid,
                new GenericCallback<Module>() {
                    public void onSuccess(Module packageConfigData) {

                        simplePanel.add(
                                new ScenarioPackageScreen(
                                        packageConfigData.getUuid(),
                                        packageConfigData.getName(),
                                        clientFactory));
                    }
                });

        return simplePanel;
    }
}
