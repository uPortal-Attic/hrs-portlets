up.jQuery(function() {
    // Reassign jQuery to $
    var $ = up.jQuery;

    var getUrl = function(data) {
        return (data.toggle == 'tax') ? taxesPDFUrl.replace('--docId--', data.id) : earningsPDFUrl.replace('--docId--', data.id);
    };

    /**
     * DataTables configuration
     */
    $.extend( true, $.fn.dataTable.defaults, {
        "sDom": "<'row'<'col-sm-12'>r>t<'row'<'col-sm-6 col-padding-override'p><'col-sm-6 text-right'i>>",
        "sPaginationType": "bootstrap",
        "oLanguage": {
            "sLengthMenu": "Show _MENU_ entries"
        }
    } );

    /* API method to get paging information */
    $.fn.dataTableExt.oApi.fnPagingInfo = function ( oSettings ) {
        return {
            "iStart":         oSettings._iDisplayStart,
            "iEnd":           oSettings.fnDisplayEnd(),
            "iLength":        oSettings._iDisplayLength,
            "iTotal":         oSettings.fnRecordsTotal(),
            "iFilteredTotal": oSettings.fnRecordsDisplay(),
            "iPage":          oSettings._iDisplayLength === -1 ?
                0 : Math.ceil( oSettings._iDisplayStart / oSettings._iDisplayLength ),
            "iTotalPages":    oSettings._iDisplayLength === -1 ?
                0 : Math.ceil( oSettings.fnRecordsDisplay() / oSettings._iDisplayLength )
        };
    };

    /* Bootstrap style pagination control */
    $.extend( $.fn.dataTableExt.oPagination, {
        "bootstrap": {
            "fnInit": function( oSettings, nPaging, fnDraw ) {
                var oLang = oSettings.oLanguage.oPaginate;
                var fnClickHandler = function ( e ) {
                    e.preventDefault();
                    if ( oSettings.oApi._fnPageChange(oSettings, e.data.action) ) {
                        fnDraw( oSettings );
                    }
                };

                $(nPaging).append(
                    '<ul class="pagination pagination-sm">'+
                        '<li class="prev disabled"><a href="#"><i class="fa fa-caret-left"></i> '+oLang.sPrevious+'</a></li>'+
                        '<li class="next disabled"><a href="#">'+oLang.sNext+' </a></li>'+
                    '</ul>'
                );
                var els = $('a', nPaging);
                $(els[0]).bind( 'click.DT', { action: "previous" }, fnClickHandler );
                $(els[1]).bind( 'click.DT', { action: "next" }, fnClickHandler );
            },

            "fnUpdate": function ( oSettings, fnDraw ) {
                var iListLength = 5;
                var oPaging = oSettings.oInstance.fnPagingInfo();
                var an = oSettings.aanFeatures.p;
                var i, ien, j, sClass, iStart, iEnd, iHalf=Math.floor(iListLength/2);

                if ( oPaging.iTotalPages < iListLength) {
                    iStart = 1;
                    iEnd = oPaging.iTotalPages;
                }
                else if ( oPaging.iPage <= iHalf ) {
                    iStart = 1;
                    iEnd = iListLength;
                } else if ( oPaging.iPage >= (oPaging.iTotalPages-iHalf) ) {
                    iStart = oPaging.iTotalPages - iListLength + 1;
                    iEnd = oPaging.iTotalPages;
                } else {
                    iStart = oPaging.iPage - iHalf + 1;
                    iEnd = iStart + iListLength - 1;
                }

                for ( i=0, ien=an.length ; i<ien ; i++ ) {
                    // Remove the middle elements
                    $('li:gt(0)', an[i]).filter(':not(:last)').remove();

                    // Add the new list items and their event handlers
                    for ( j=iStart ; j<=iEnd ; j++ ) {
                        sClass = (j==oPaging.iPage+1) ? 'class="active"' : '';
                        $('<li '+sClass+'><a href="#">'+j+'</a></li>')
                            .insertBefore( $('li:last', an[i])[0] )
                            .bind('click', function (e) {
                                e.preventDefault();
                                oSettings._iDisplayStart = (parseInt($('a', this).text(),10)-1) * oPaging.iLength;
                                fnDraw( oSettings );
                            });
                    }

                    // Add / remove disabled classes from the static elements
                    if ( oPaging.iPage === 0 ) {
                        $('li:first', an[i]).addClass('disabled');
                    } else {
                        $('li:first', an[i]).removeClass('disabled');
                    }

                    if ( oPaging.iPage === oPaging.iTotalPages-1 || oPaging.iTotalPages === 0 ) {
                        $('li:last', an[i]).addClass('disabled');
                    } else {
                        $('li:last', an[i]).removeClass('disabled');
                    }
                }
            }
        }
    } );

    // Datatables 
    var eTable = $(earningsTable).dataTable( {
        "bProcessing": true,
        "sAjaxSource": earningsUrl,
        "sAjaxDataProp": "report",
        "aaSorting": [[ 3, "desc" ]],
        "aoColumnDefs": [
            {
                "sWidth": "15%",
                "aTargets": [ 0 ],
                "mData": "paid",
                "mRender": function( data, type, full ) {
                    return '<a href="' + getUrl({toggle: 'earnings', id: full.docId}) + '" title="View earnings document" class="">' + data + '</a>';
                }
            },
            {
                "sWidth": "70%",
                "aTargets": [ 1 ],
                "mData": "earned",
                "mRender": function( data, type, full ) {
                    return '<a href="' + getUrl({toggle: 'tax', id: full.docId}) + '" title="View tax document" class="">' + data + '</a>';
                }
            },
            {
                "sWidth": "15%",
                "aTargets": [ 2 ],
                "mData": "amount",
                "sClass": "amount",
                "bVisible": false
            }
        ],
        "fnInitComplete": function(oSettings, json) {
            /**
             * Place all event handler code here
             */
            $('#earnings-toggle').on( 'change', function (e) {
                e.preventDefault();

                var bVis = eTable.fnSettings().aoColumns[2].bVisible;
                eTable.fnSetColumnVis( 2, bVis ? false : true );
            } );
            //( 'DataTables has finished its initialisation.' );
        }
    });

    var tTable = $(taxesTable).dataTable( {
        "bProcessing": true,
        "sAjaxSource": taxesUrl,
        "sAjaxDataProp": "report",
        "aoColumnDefs": [
            {
                "sWidth": "15%",
                "aTargets": [ 0 ],
                "mData": "year",
                "mRender": function( data, type, full ) {
                    return '<a href="' + getUrl({toggle: 'tax', id: full.docId}) + '" title="View Tax document" class="">' + data + '</a>';
                }
            },
            {
                "sWidth": "85%",
                "aTargets": [ 1 ],
                "mData": "fullTitle",
                "mRender": function( data, type, full ) {
                    return '<a href="' + getUrl({toggle: 'tax', id: full.docId}) + '" title="View Tax document" class="">' + data + '</a>';
                }
            }
        ]
    });
});