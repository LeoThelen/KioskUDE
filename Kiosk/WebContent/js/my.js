
// store filter for each group
var filters = {};

$('.filters').on('click', '.button', function(event) {
	var $button = $(event.currentTarget);
	// get group key
	var $buttonGroup = $button.parents('.button-group');
	var filterGroup = $buttonGroup.attr('data-filter-group');
	// set filter for group
	filters[filterGroup] = $button.attr('data-filter');
	// combine filters
	var filterValue = concatValues(filters);
	// set filter for Isotope
	$grid.isotope({
		filter : filterValue
	});
});

// change is-checked class on buttons
$('.button-group').each(function(i, buttonGroup) {
	var $buttonGroup = $(buttonGroup);
	$buttonGroup.on('click', 'button', function(event) {
		$buttonGroup.find('.is-checked').removeClass('is-checked');
		var $button = $(event.currentTarget);
		$button.addClass('is-checked');
	});
});

// flatten object by concatting values
function concatValues(obj) {
	var value = '';
	for ( var prop in obj) {
		value += obj[prop];
	}
	return value;
}

// resize grid-items on click
var $grid = $('.grid').isotope({
	itemSelector : '.grid-item',

});

$grid.on('click', '.grid-item', function() {
	// change size of item by toggling gigante class
	$('.gigante').toggleClass('gigante');
	$(this).toggleClass('gigante');
	$.get('description?id='+$(this).attr('id'), function(data) {
		$('#description').html(data);	
	})
	$grid.isotope('layout');
});

