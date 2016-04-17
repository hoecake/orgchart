(function(){
	try{
		var unitId = url.templateArgs['id'];
		var unitDisplayName = json.get('data').get('displayName');
		var unitWeight = json.get('data').get('weight');
		var unit = orgchart.getUnit(unitId);
		model.unit = unit.update(unitDisplayName, unitWeight);
		status.code = 200;
	}
	catch (e) {
		status.code = 500;
		status.message = e.message;
		model.message = e.message;
	}
})();