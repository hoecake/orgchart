<import resource="classpath:/alfresco/web-extension/site-webscripts/com/alvexcore/js/alvex-config.lib.js">

var uiConfig = Alvex.configs.getConfig('orgchart', 'orgchart-view.default');
var syncConfig = Alvex.configs.getConfig('orgchart', 'orgchart-sync.default');

model.config = {
    defaultRoleName: uiConfig.props['alvexoc:defaultRoleName'] ? uiConfig.props['alvexoc:defaultRoleName'] : '',
    uiConfigNodeRef: uiConfig.nodeRef,
    syncConfigNodeRef: syncConfig.nodeRef,
    syncSource: syncConfig.props['alvexoc:syncSource']
}

var conn = remote.connect("alfresco");
var resp = eval('(' + conn.get("/api/alvex/server") + ')');

model.alvexVersion = resp.version;
model.alvexEdition = resp.edition;
model.alvexCodename = resp.codename;

/*

    var pew = '';

model.config = {
    defaultRoleName: '',
    uiConfigNodeRef: 'workspace://SpacesStore/fe663ed3-d2f0-48e3-96b6-0fb20543cd60',
    syncConfigNodeRef: 'workspace://SpacesStore/801d96b4-3a41-4a9f-b72f-27b205eb08f0',
    syncSource: 'none'
}

model.alvexVersion = 'dev';
model.alvexEdition = 'dev';
model.alvexCodename = 'dev';
    */