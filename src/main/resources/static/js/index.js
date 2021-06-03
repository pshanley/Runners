var username = document.getElementById('myscript').getAttribute('username')

$.ajax({
	url: "linechartdata",
	type: "get", //send it through get method
	data: {
	  "name": username
	},
	success: function(result){
		var date = JSON.parse(result).date;
		var followers = JSON.parse(result).followers;
		drawLineChart(date, followers);

	}
});

/* for line chart */
function drawLineChart(date, followers){
	Highcharts.chart('container', {
	    chart: {
	        type: 'line',
	        width: 500
	    },
	    
	    title: {
	        text: 'Instagram Followers by Day'
	    },
	
	    xAxis: {
	        categories: date
	    },
        yAxis: {
            title: {
                text: 'Instagram Followers'
            }
        },

        credits: {
            enabled: false
        },

	    tooltip: {
	        formatter: function() {
	          return '<strong>'+this.x+': </strong>'+ this.y;
	        }
	    },
	
	    series: [{
	        data: followers,
	        showInLegend: false,
	    }]
	});
}

