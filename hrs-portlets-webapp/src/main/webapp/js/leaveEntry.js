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
            changed: false,
            errors: [],
            patterns: {
                hh: /^\d{1,2}$/i,
                // mm: /^:\d{2}$/i,
                mm: /^^0*?:\d{2}$/i,
                hhmmss: /^\d{1,}:\d{2}(:\d{2})?$/i
            },
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
            var sign = (secs < 0) ? '-' : '';
            secs = Math.abs(secs);

            var hours = Math.floor(secs / (60 * 60));

            var divisor_for_minutes = secs % (60 * 60);
            var minutes = Math.floor(divisor_for_minutes / 60);

            var divisor_for_seconds = divisor_for_minutes % 60;
            var seconds = Math.ceil(divisor_for_seconds);

            var obj = {
                "sign": sign,
                "h": hours,
                "m": (minutes < 10) ? '0' + minutes : minutes,
                "s": (seconds < 10) ? '0' + seconds : seconds
            };
            return obj;
        };

        /**
         * Takes in a HH:MM string and converts it to seconds.
         * @param  {string} hm HH:MM string
         * @return {integer}   Returns the time in seconds
         */
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

                return formatTime(t);
        };

        var subtractTime = function(first, second) {
                var f = timeToSeconds(first);
                var s = timeToSeconds(second === '' ? '0:00' : second);

                var t = secondsToTime(f - s);
                return formatTime(t);
        };

        var formatTime = function(timeObj) {
            if (timeObj.m.length == 1) {
                timeObj.m = '0'+timeObj.m;
            }
            // console.log('to - ', timeObj);
            return timeObj.sign + timeObj.h + ':' + timeObj.m;
        };

        var columnTotals = function() {
            var idx = settings.columnIndex + 1; // the n-th child selector starts with 1 not 0 so we need to increase the index.
            var hours = settings.table.find('tbody tr:first td:nth-child('+idx+')')[0].innerHTML;
            var total = settings.table.find('tbody tr:last td:nth-child('+idx+')');

            /**
             * Now that we have the column index, we need to loop thru all cells and get their values.  The 
             * values will be stored in an array which will be looped over later on to calculate the totals.
             */
            var cells = settings.table.find('tr td:nth-child('+idx+') input');
            var cellValues = [];
                cellValues.push(hours);
            $.each( cells, function(index, value) {
                   if (value.value !== '') {
                        cellValues.push(value.value);
                   }

            });

            // Loop thru each item to get their values
            $.each(cellValues, function(index, value) {
                    hours = calculateTime(hours, value);
            });

            if (timeToSeconds(hours) > 24*60*60) {
                highlightField();
                total.addClass('time-total-error');
                updateAlert('overDayTotal');
                // highlightField(total);
            } else {
                total.removeClass('time-total-error');
            }
            total.text(calculateTotal(cellValues));
        };
        
        var rowTotals = function() {
            var rowIndex = settings.rowIndex + 1; // the n-th child selector starts with 1 not 0 so we need to increase the index.
            var inputTotals = '0:00';

            /**
             * Now that we have the column index, we need to loop thru all cells and get their values.  The 
             * values will be stored in an array which will be looped over later on to calculate the totals.
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

            if (timeToSeconds(inputTotals) > 40*60*60) {
                totalCell.addClass('leave-entry-total-error');
            } else {
                totalCell.removeClass('leave-entry-total-error');
            }
            
            totalCell.text(calculateTotal(cellValues));
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

            settings.grandTotalCell.text(calculateTotal(cellValues));
        };

        var calculateTotal = function(arr) {
            var total = '0:00';

            $.each(arr, function(index, value) {
                    total = calculateTime(total, value);
            });
            return total;
        };


        var calculateCumulativeTotals = function() {
            // var rowIndex = settings.rowIndex + 1; // the n-th child selector starts 
            // with 1 not 0 so we need to increase the index.
            var inputTotals = '0:00',
                sickTotal = '0:00',
                vacationTotal = '0:00';

            /**
             * Now that we have the column index, we need to loop thru all cells and get their values. 
             * The values will be stored in an array which will be looped over later on to calculate the totals.
             */
            var sickCell = $('.period-balances td')[2];
            var vacationCell = $('.period-balances td')[3];
            var sickTotals = $('.Sick-total');
            var vacationTotals = $('.Vacation-total');
            var sickValues = [];
            var vacationValues = [];

            $.each( sickTotals, function(index, value) {
                    sickValues.push(value.innerHTML);
            });
            $.each( vacationTotals, function(index, value) {
                    vacationValues.push(value.innerHTML);
            });

            // Loop thru each item to get their values
            $.each(sickValues, function(index, value) {
                    sickTotal = calculateTime(sickTotal, value);
            });
            $.each(vacationValues, function(index, value) {
                    vacationTotal = calculateTime(vacationTotal, value);
            });

            sickCell.innerHTML = calculateTotal(sickValues);
            vacationCell.innerHTML = calculateTotal(vacationValues);
        };

        var updateBalanceGrandTotals = function() {
            // console.log('updateBalanceGrandTotals');
            var sickColumn = [],
                vacationColumn = [],
                sickBalanceCell = $('.end-balances td')[2],
                vacationBalanceCell = $('.end-balances td')[3];
            
            sickColumn = $('.leave-entry-balances table tbody tr td:nth-child(3)');
            vacationColumn = $('.leave-entry-balances table tbody tr td:nth-child(4)');

            sickBalanceCell.innerHTML = subtractTime(sickColumn[0].innerHTML, sickColumn[1].innerHTML);
            vacationBalanceCell.innerHTML = subtractTime(vacationColumn[0].innerHTML, vacationColumn[1].innerHTML);
        };

        var cleanFormat = function(v) {
            // console.log('cleanFormat v', v);
            var split = v.split(":");

            if (settings.patterns.hh.test(v)) {
                return v + ':00';
            } else if (settings.patterns.mm.test(v)) {
                if (split[1] > 60 ) {
                    return formatTime(secondsToTime(timeToSeconds(v)));
                } else {
                    if(split[0]) {
                        if (split[0].length === 1) {
                            return v;
                        } else if(split[0].length > 1) {
                            return '0:' + split[1];
                        }
                    } else if (!split[0]) {
                        return '0' + v;
                    } else {
                        return v;
                    }
                }
            } else {
                if (split[1] > 60) {
                    return calculateTime(split[0] + ':00', formatTime(secondsToTime(timeToSeconds('0:'+split[1]))) );
                }
                return v;
            }
        };

        var validateEntry = function(val) {
            if (val == 'on' || !val) {
                // return '00:00';
                return true;
            } else {
                return settings.patterns.hhmmss.test(val);
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
            if (i != -1) {
                settings.errors.splice(i, 1);
            }
            checkErrors();
        };

        var checkErrors = function() {
            if (settings.errors.length > 0) {
                updateAlert('errors');
            } else {
                updateAlert();
                // $(self).find('a.leave-entry-submit').removeClass('disabled');
            }

            toggleSubmit();
        };

        /**
         * Enable/Disable the submit button based on error status
         * @return {none} No return
         */
        var toggleSubmit = function() {
            changeCheck();

            var submit = $(self).find('a.leave-entry-submit');
            if (settings.errors.length > 0) {
                submit.addClass('disabled');
            } else if (settings.changed === true ) {
                submit.removeClass('disabled');
            } else {
                submit.addClass('disabled');
            }

        };

        var changeCheck = function() {
            var fields = $('table.leave-entry-table').find(':text');
            
            $.each(fields, function(index, value) {
                if (value.defaultValue != value.value) {
                    settings.changed = true;
                    return false;
                } else {
                    settings.changed = false;
                    return true;
                }
            });
        };

        var updateAlert = function(state, msg) {
            //console.log('state', state);
            switch(state) {
                case 'success':
                    settings.alertDiv.addClass(settings.messages.success.alertClass).text((msg) ? msg : settings.messages.success.msg);
                    settings.alertDiv.show();
                    break;
                case 'errors':
                    settings.alertDiv.addClass(settings.messages.error.alertClass).text((msg) ? msg : settings.messages.error.msg);
                    settings.alertDiv.show();
                    break;
                case 'overDayTotal':
                    settings.alertDiv.addClass(settings.messages.overDayTotal.alertClass).text((msg) ? msg : settings.messages.overDayTotal.msg);
                    settings.alertDiv.show();
                    break;
                case 'overWeekTotal':
                    settings.alertDiv.addClass(settings.messages.overWeekTotal.alertClass).text((msg) ? msg : settings.messages.overWeekTotal.msg);
                    settings.alertDiv.show();
                    break;
                case 'failed':
                    settings.alertDiv.addClass(settings.messages.failed.alertClass).text((msg) ? msg : settings.messages.failed.msg);
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
            var data = self.serialize();
            var jqxhr = $.post( self.attr('action'), data , function( data ) {
                if (data.success) {
                    updateAlert('success');
                } else {
                    updateAlert('failed', data.error_message);
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
            $(this).bind(settings.event, function(evt) {
                // if($(evt.target).not(":text")) {
                //     //var returnVal = confirm("Are you sure?");
                //     //$(this).attr("checked", returnVal);
                //     console.log('checked');
                //     return;
                // }
                settings.input = $(evt.target);

                // evt.target.value = cleanFormat(evt.target.value);
                // var tmp = cleanFormat(evt.target.value);
                // console.log('tmp: ', tmp);
                settings.input[0].value = cleanFormat(evt.target.value);

                // if (validateEntry(evt.target.value)) {
                if (validateEntry(settings.input[0].value)) {
                    unHighlightField();
                    //settings.input.removeClass('leave-entry-error');
                    setIndexes();
                    rowTotals();
                    columnTotals();
                    grandTotal();
                    calculateCumulativeTotals();
                    updateBalanceGrandTotals();

                // } else if (evt.target.value === '') {
                } else if (settings.input[0].value === '') {
                    unHighlightField();
                } else {
                    highlightField();
                }

                return false;
            });

            /**
             * Add submit form handler to the "submit" button 
             */
            $(self).find('a.leave-entry-submit').click(function(evt) {
                evt.preventDefault();
                submitForm();
            });

            $(self).find('input.toggleWeekends').click(function() {
                $("table.leave-entry-table tr th:nth-child(2), table.leave-entry-table tr th:nth-child(3), table.leave-entry-table tr td:nth-child(2), table.leave-entry-table tr td:nth-child(3)").toggle(this.checked);
            //     return false;
            });
            //// console.log(submitButton);
        });

        return {
            /**
             * Public Methods
             */
        };
    };
})($);

// $( document ).ready(function() {
//     // console.log('dom rdy - attach to leave-entry-form', $('form.leave-entry-form'));
//         $('form.leave-entry-form').leaveEntryTimeTotalCalculations();
//         // $('table.leave-entry-table').leaveEntryTimeTotalCalculations();
// });