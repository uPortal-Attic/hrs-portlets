(function($){
    $.fn.leaveEntryTimeTotalCalculations = function(callerSettings) {
        if (this.size()===0) {
            return;
        } // Empty set so do nothing.

        var self = this;

        var settings = $.extend({
            event: 'change',
            table: '',
            input: '',
            rowIndex: '',
            columnIndex: '',
            rowTotal: '0:00',
            columnTotal: '0:00',
            grandTotalCell: '',
            errors: [],
            alertDiv: $(self).find('div.leave-entry-alerts'),
            messages: {
                error: {
                    alertClass: 'alert-danger',
                    msg: 'Resolve exceptions or see your time manager'
                },
                overDayTotal: {
                    alertClass: 'alert-danger',
                    msg: 'You can not enter hours which will exceed a daily total more than 24 hours.  Please recheck your entries and try again.'
                },
                overWeekTotal: {
                    alertClass: 'alert-danger',
                    msg: 'You can not enter hours which will exceed a weekly total more than 40 hours.  Please recheck your entries and try again.'
                },
                success: {
                    alertClass: 'alert-success',
                    msg: 'Changes have been saved.'
                },
                failed: {
                    alertClass: 'alert-danger',
                    msg: 'Unable to save your changes at this time.  Please check your entries and try again.'
                }
            },
            debug: false
        }, callerSettings || {});
        
        var secondsToTime = function(secs) {
            var hours = Math.floor(secs / (60 * 60));

            var divisor_for_minutes = secs % (60 * 60);
            var minutes = Math.floor(divisor_for_minutes / 60);

            var divisor_for_seconds = divisor_for_minutes % 60;
            var seconds = Math.ceil(divisor_for_seconds);

            var obj = {
                "h": hours,
                "m": (minutes < 10) ? '0' + minutes : minutes,
                "s": (seconds < 10) ? '0' + seconds : seconds
            };
            return obj;
        };

        var timeToSeconds = function(hm) {
            //    var hms = '02:00';   // your input string
            if (!hm) {
                    hm = '0:00';
            }
            var a = hm.split(':'); // split it at the colons

            // minutes are worth 60 seconds. Hours are worth 60 minutes.
            var seconds = (+a[0]) * 60 * 60 + (+a[1]) * 60;
            return seconds;
        };

        var calculateTime = function(first, second) {
                /**
                 * Extend this to do smart calculations if the first is greater than the second then subtract time, otherwise add
                 * @type {[type]}
                 */
                var f = timeToSeconds(first);
                var s = timeToSeconds(second === '' ? '0:00' : second);
                var t = secondsToTime(f + s);
//                if (t.h.length == 1) {
//                        t.h = '0'+t.h;
//                }
                if (t.m.length == 1) {
                        t.m = '0'+t.m;
                }
                return t.h + ':' + t.m;
        };

        var columnTotals = function() {
// console.log('columnTotals');
            var idx = settings.columnIndex + 1; // the n-th child selector starts with 1 not 0 so we need to increase the index.
//  console.log(settings.table);
            var hours = settings.table.find('tbody tr:first td:nth-child('+idx+')').text();
            var total = settings.table.find('tbody tr:last td:nth-child('+idx+')');
// console.log('col - hours', hours);
            /**
             * Now that we have the column index, we need to loop thru all cells and get their values.  The values will be stored in an array
             * which will be looped over later on to calculate the totals.
             */
            var cells = settings.table.find('tr td:nth-child('+idx+') input');
            var cellValues = [];
            $.each( cells, function(index, value) {
//                    if (value.value === '') {
//                        value.value = '0:00';
//                    }
                    cellValues.push(value.value);
            });

            // Loop thru each item to get their values
            $.each(cellValues, function(index, value) {
                    hours = calculateTime(hours, value);
            });
            // console.log('col - total', total);
            // console.log('col - hours', hours);

            if (timeToSeconds(hours) > 24*60*60) {
                // console.log('display 24-hour error');
                highlightField();
                total.addClass('time-total-error');
                updateAlert('overDayTotal');
                // highlightField(total);
            } else {
                total.removeClass('time-total-error');
            }
            total.text(hours);
        };
        
        var rowTotals = function() {
            var rowIndex = settings.rowIndex + 1; // the n-th child selector starts with 1 not 0 so we need to increase the index.
            var inputTotals = '0:00';

            /**
             * Now that we have the column index, we need to loop thru all cells and get their values.  The values will be stored in an array
             * which will be looped over later on to calculate the totals.
             */
            var totalCell = settings.table.find('tbody tr:nth-child('+rowIndex+') td:last');
            var cells = settings.table.find('tbody tr:nth-child('+rowIndex+') td input');

            var cellValues = [];
            $.each( cells, function(index, value) {
//                    if (value.value === '') {
//                        value.value = '0:00';
//                    }
                    cellValues.push(value.value);
            });

            // Loop thru each item to get their values
            $.each(cellValues, function(index, value) {
                    inputTotals = calculateTime(inputTotals, value);
            });
            // console.log('row - totalCell', totalCell);
            // console.log('row - inputTotals', inputTotals);
            if (timeToSeconds(inputTotals) > 40*60*60) {
                totalCell.addClass('leave-entry-total-error');
            } else {
                totalCell.removeClass('leave-entry-total-error');
            }
            totalCell.text(inputTotals);
        };
        
        var grandTotal = function() {
            var lastColumnIndex = settings.table.find('tbody tr:first td:last').index() + 1;
            var lastRowIndex = settings.table.find('tbody tr:last').index() + 1;
            var grandTotal = '0:00';

            // Calculate the column values
            var cells = settings.table.find('tbody tr td:nth-child('+lastColumnIndex+')').not(':last');
            var cellValues = [];

            $.each( cells, function(index, value) {
                    cellValues.push(value.innerHTML);
            });

            // Loop thru each item to get their values
            $.each(cellValues, function(index, value) {
                    grandTotal = calculateTime(grandTotal, value);
            });

            settings.grandTotalCell.text(grandTotal);
        };

        var validateEntry = function(val) {
            if (val == 'on' || !val) {
                // return '00:00';
                return true;
            } else {
                //var pattern = /^\d{2,}:\d{2}(:\d{2})?$/gi;
                var pattern = /^\d{1,}:\d{2}(:\d{2})?$/gi;
                return pattern.test(val);
            }
        };

        var validateDayTotal = function() {
            // console.log('validateDayTotal');
        };

        var highlightField = function(jqEl) {
            // jqEl.addClass('leave-entry-error');
            // console.log('highlightField');
            settings.errors.push(settings.input[0].id);
            settings.input.addClass('leave-entry-error');
            settings.input.closest('td').addClass('danger');
            checkErrors();
            //// console.log('settings.input[0].id',settings.input[0].id);
            
        };
        var unHighlightField = function(jqEl) {
            settings.input.removeClass('leave-entry-error');
            settings.input.closest('td').removeClass('danger');
            var i = settings.errors.indexOf(settings.input[0].id);
            if (i > 0) {
                settings.errors.splice(i, 1);
            }
            checkErrors();
        };

        var checkErrors = function() {
            if (settings.errors.length > 0) {
                updateAlert('errors');
            } else {
                updateAlert();
                $(self).find('a.leave-entry-submit').removeClass('disabled');
            }
        };

        var updateAlert = function(state) {
            //console.log('state', state);
            switch(state) {
                case 'success':
                    settings.alertDiv.addClass(settings.messages.success.alertClass).text(settings.messages.success.msg);
                    settings.alertDiv.show();
                    break;
                case 'errors':
                    settings.alertDiv.addClass(settings.messages.error.alertClass).text(settings.messages.error.msg);
                    settings.alertDiv.show();
                    break;
                case 'overDayTotal':
                    settings.alertDiv.addClass(settings.messages.overDayTotal.alertClass).text(settings.messages.overDayTotal.msg);
                    settings.alertDiv.show();
                    break;
                case 'overWeekTotal':
                    settings.alertDiv.addClass(settings.messages.overWeekTotal.alertClass).text(settings.messages.overWeekTotal.msg);
                    settings.alertDiv.show();
                    break;
                case 'failed':
                    settings.alertDiv.addClass(settings.messages.failed.alertClass).text(settings.messages.failed.msg);
                    settings.alertDiv.show();
                    break;
                default:
                    settings.alertDiv.removeClass (function (index, css) {
                        return (css.match (/\balert-\S+/g) || []).join(' ');
                    });
                    settings.alertDiv.hide();
            }
        };

        var setIndexes = function(el) {
            //  console.log(settings.input);
            settings.table = settings.input.closest('table');
            // settings.columnIndex = settings.input.parent().index();
            settings.columnIndex = settings.input.closest('td').index();
            settings.rowIndex = settings.input.closest('tr').index();
            settings.grandTotalCell = settings.table.find('td.leave-entry-grand-total');
        };

        var submitForm = function() {
            // console.log('submitForm');
            //  console.log(self.attr('action'));
            //  console.log( self.serialize() );
            //$.post( "test.php", $( "#testform" ).serialize() );
            var jqxhr = $.post( self.attr('action'), self.serialize(), function( data ) {
                // console.log('AJAX data', data);
                if (data.success) {
                    updateAlert('success');
                } else {
                    //{"error_message":"Invalid time value, enter as hh:mm","fields":["leaveItem_202_2013-12-14_Pluto_96_n92_14_"]}
                    updateAlert('failed');
                }

                
            })
            .fail(function() {
                alert( "error" );
            });
        };

        // var submitButtonHandler = function() {
        //     var sb = $(self).find('a.leave-entry-submit');
        //     // console.log('sb', sb);
        // };

        this.each(function() {
            // console.log('this', this);
            // console.log('settings.event', settings.event);
            $(this).bind(settings.event, function(evt) {
                // if($(evt.target).not(":text")) {
                //     //var returnVal = confirm("Are you sure?");
                //     //$(this).attr("checked", returnVal);
                //     console.log('checked');
                //     return;
                // }
                settings.input = $(evt.target);

                if (validateEntry(evt.target.value)) {
                    unHighlightField();
                    //settings.input.removeClass('leave-entry-error');
                    setIndexes();
                    rowTotals();
                    columnTotals();
                    grandTotal();
                } else if (evt.target.value === '') {
                    // console.log('emtpty field');
                    unHighlightField();
                } else {
                        highlightField();
                }

                return false;
            });

            /**
             * Add submit form handler to the "submit" button 
             */
            // console.log('bind submit');
            $(self).find('a.leave-entry-submit').click(function(evt) {
                evt.preventDefault();
                submitForm();
            });

            $(self).find('input.toggleWeekends').click(function() {
            //     console.log('show weekends');
                $("table.leave-entry-table tr th:nth-child(2), table.leave-entry-table tr th:nth-child(3), table.leave-entry-table tr td:nth-child(2), table.leave-entry-table tr td:nth-child(3)").toggle(this.checked);
            //     return false;
            });
            //// console.log(submitButton);
        });

        return {
            setGrandTotal: function() {
                // console.log('setGrandTotal');
            }
        };
    };
})($);

// $( document ).ready(function() {
//     // console.log('dom rdy - attach to leave-entry-form', $('form.leave-entry-form'));
//         $('form.leave-entry-form').leaveEntryTimeTotalCalculations();
//         // $('table.leave-entry-table').leaveEntryTimeTotalCalculations();
// });