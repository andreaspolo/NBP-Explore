(function( $ ) {
 
	/**
	 * A default settings
	 */
	var defaults = {
		labels: [], // empty labels array
		datas: [] // empty data array
	};

	/**
	 * 
	 */
    $.fn.currencyChart = function(options) {
    	// prepare options
    	var settings = $.extend( {}, defaults, options );
    	// check if element is correct, must be a canvas.
    	if (!this.is('canvas')) {
    		console.log('currencyChart error - a chart element must be a "canvas", it can NOT be "' + this.get(0).tagName + '"');
    		return;
    	}
    	// element ok
    	console.log('currencyChart notice - element OK.');
    	// retrieve an element id to create a canvas context
    	settings.id = this.attr('id');
    	console.log('currencyChart notice - element id = ' + settings.id);
    	// create a context
    	var ctx = document.getElementById(settings.id).getContext('2d');
    	// create a chart
    	var myChart = new Chart(ctx, {
			type: 'line',
			data: {
				labels: settings.labels,
				datasets: buildDataset(settings.datas)
			}
    	});
    };
 
    /**
     * Building a dataset object. As a parameter function gets a java map
     * @param datas
     * @returns
     */
    function buildDataset(datas) {
    	
    	// dataset
    	var ds = [];
    	
    	for (var x in datas) {
    		ds.push({
    			label: datas[x].currency,
    	    	data: datas[x].rates,
    	    	fill: true,
    	    	backgroundColor: currencyColor(datas[x].currency)
    		});
    	};
    	
    	return ds;
    }
    
    /**
     * Getting a color for a currency.
     * @param c Currency code
     * @returns
     */
    function currencyColor(c) {
    	return (cc[c]!=undefined)?cc[c]:"rgba(153,255,51,0.2)"
    }
    
    /**
     * Predefined colors for currencies.
     */
    var cc = {
		'EUR': "rgba(9,20,162,0.2)",
		'CHF': "rgba(238,6,12,0.2)",
		'USD': "rgba(108,11,18,0.2)",
		'CAD': "rgba(113,6,10,0.2)",
		'HUF': "rgba(18,105,14,0.2)",
		'GBP': "rgba(106,13,15,0.2)",
		'CZK': "rgba(21,30,138,0.2)"
	};
    
}( jQuery ));