/**Isotope Filter*/
// store filter for each group
var buttonFilter;
// quick search regex
var qsRegex;
var filters = {};

// init Isotope
var $grid = $('.grid').isotope({
  itemSelector: '.grid-item',
  filter: function() {
    var $this = $(this);
    var searchResult = qsRegex ? $this.attr('gametitle').match( qsRegex ) : true;
    var buttonResult = buttonFilter ? $this.is( buttonFilter ) : true;
    return searchResult && buttonResult;
  },
});

// use value of search field to filter the grid items
var $quicksearch = $('.quicksearch').keyup( debounce( function() {
  qsRegex = new RegExp( $quicksearch.val(), 'gi' );
  $grid.isotope();
}) );

//debounce so filtering doesn't happen every millisecond
function debounce( fn, threshold ) {
  var timeout;
  threshold = threshold || 200;
  return function debounced() {
    clearTimeout( timeout );
    var args = arguments;
    var _this = this;
    function delayed() {
      fn.apply( _this, args );
    }
    timeout = setTimeout( delayed, threshold );
  };
}

$('.filters').on('change', function( event ) {
	var $select = $( event.target );
	//get group key
	var filterGroup = $select.attr('data-filter-group');
	//set filter for group
	filters [ filterGroup ] = event.target.value;
	//combine filters
	buttonFilter = concatValues( filters );
	// set filter for Isotope
	$grid.isotope();
});

// flatten object by concatting values
function concatValues( obj ) {
  var value = '';
  for ( var prop in obj ) {
    value += obj[ prop ];
  }
  return value;
}

//smooth scroll back-to-top
$("#back-to-top").on('click', function() {
	$('html, body').animate({scrollTop:0}, 330);
});

$grid.on('click', '.grid-item', function() {
	var thumblink= $(this).children().attr('src');
  	$('#delID').val($(this).attr('id'));
	//scroll description into view
	var sTop=$("#left").offset().top;
	$('html, body').animate({scrollTop:sTop}, 330);
    //set active only currently clicked item
	$('.grid-item-active').toggleClass('grid-item-active');
	$(this).toggleClass('grid-item-active');
	//load description page
	$.get('description?id='+$(this).attr('id'), function(data) {
		$('#description').html(data);
		var currentEN = $(".EN").css("display");
		var currentDE = $(".DE").css("display");
		$('.EN').css('display', currentEN);
		$('.DE').css('display', currentDE);
	});
	$grid.isotope('layout');
});

//change is-checked class on buttons in tagFormular
$('.button-group').each( function( i, buttonGroup ) {
  var $buttonGroup = $( buttonGroup );
  $buttonGroup.on( 'click', 'button', function() {
    $buttonGroup.find('.is-checked').removeClass('is-checked');
    $( this ).addClass('is-checked');
  });
});

//add classes when down-scrolled
$(document).ready(function () {
    $(window).scroll(function () {
        if ($(document).scrollTop() > 1) {
        	
        	$("body").addClass("down-scrolled");
        	$("#description").addClass("down-scrolled");
         	$("#back-to-top").removeClass("invisible");
        	var $styleB = $('<style id="dynamicStyle">::-webkit-scrollbar-thumb {background: #ac0;}</style>');
        	$('#dynamicStyle').replaceWith($styleB);
        	
        } else {
        	$("body").removeClass("down-scrolled");
        	var $styleA = $('<style id="dynamicStyle">::-webkit-scrollbar-thumb {background: #aaa;}</style>');
        	$('#dynamicStyle').replaceWith($styleA);
        	$("#description").removeClass("down-scrolled");
        	$("#back-to-top").addClass("invisible");
        }
    });
});

document.getElementById('DE').onclick = function () {switchLanguageToDE()};
function switchLanguageToDE () {
		$('.DE').css('display', 'inline-block');
		$('.EN').css('display', 'none');
}

document.getElementById('EN').onclick = function () {switchLanguageToEN()};	
function switchLanguageToEN () {
		$('.EN').css('display', 'inline-block');
		$('.DE').css('display', 'none');
}