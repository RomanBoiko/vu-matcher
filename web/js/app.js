$(document).ready( function() {
  $('#example').dataTable( {
    "bJQueryUI": true,
    "sPaginationType": "full_numbers",
    "bProcessing": true,
    "sAjaxSource": 'json/active_records'
    });
  }
);
