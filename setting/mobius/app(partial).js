/**
 * Copyright (c) 2015, OCEAN
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products derived from this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/**
 * @file Main code of Mobius Yellow. Role of flow router
 * @copyright KETI Korea 2015, OCEAN
 * @author Il Yeup Ahn [iyahn@keti.re.kr]
 */

var content = '';

app.get(onem2mParser, function (request, response) {
    var fullBody = '';
    request.on('data', function (chunk) {
        fullBody += chunk.toString();
    });
    request.on('end', function () {
        request.body = fullBody;
        if (request.query.fu == null) {
            request.query.fu = 2;
        }
        if (request.query.rcn == null) {
            request.query.rcn = 1;
        }
        if (request.query.rt == null) {
            request.query.rt = 3;
        }

        check_body_format(request);

        request.url = request.url.replace('%23', '#'); // convert '%23' to '#' of url
        request.hash = url.parse(request.url).hash;
        var absolute_url = request.url.replace(/\/~\/[^\/]+\/?/, '/').split('#')[0];
        absolute_url = absolute_url.replace(/\/_/, '/' + usecsebase);
        var absolute_url_arr = absolute_url.split('/');
        db_sql.get_ri_sri(absolute_url_arr[1].split('?')[0], function (err, results) {
            absolute_url = (results.length == 0) ? absolute_url : absolute_url.replace('/' + absolute_url_arr[1], results[0].ri);

            if (url.parse(absolute_url).pathname.split('/')[1] == usecsebase) {
                request.url = absolute_url;
                if ((request.query.fu == 1 || request.query.fu == 2) &&
                    (request.query.rcn == 1 || request.query.rcn == 4 || request.query.rcn == 5 || request.query.rcn == 6 || request.query.rcn == 7)) {
                    lookup_retrieve(request, response);
                }
                else {
                    responder.error_result(request, response, 405, 4005, 'OPERATION_NOT_ALLOWED (rcn or fu query is not supported at GET request)');
                }
            }
            else {
                check_csr(absolute_url, function (rsc, body_Obj) {
                    if (rsc == '0') {
                        responder.error_result(request, response, 500, 5000, body_Obj['dbg']);
                    }
                    else if (rsc == '1') {
                        forward_http(body_Obj.forwardcbhost, body_Obj.forwardcbport, request, response);
                    }
                    else if (rsc == '2') {
                        responder.error_result(request, response, 200, 5000, content.substring(content.indexOf("<content>")+9,content.indexOf("</content>")));
                    }
                    else {
                        responder.error_result(request, response, 500, 5000, body_Obj['dbg']);
                    }
                });
            }
        });
    });
});

function forward_http(forwardcbhost, forwardcbport, request, response) {
    var options = {
        hostname: forwardcbhost,
        port: forwardcbport,
        path: request.url,
        method: request.method,
        headers: request.headers
    };

    var req = http.request(options, function (res) {
        var fullBody = '';
        res.on('data', function (chunk) {
            fullBody += chunk.toString();
content = fullBody;
        });

        res.on('end', function () {
       /*     console.log('[Forward response : ' + res.statusCode + ']');

            //response.headers = res.headers;
            if (res.headers['content-type']) {
                response.setHeader('Content-Type', res.headers['content-type']);
            }
            if (res.headers['x-m2m-ri']) {
                response.setHeader('X-M2M-RI', res.headers['x-m2m-ri']);
            }
            if (res.headers['x-m2m-rsc']) {
                response.setHeader('X-M2M-RSC', res.headers['x-m2m-rsc']);
            }
            if (res.headers['content-location']) {
                response.setHeader('Content-Location', res.headers['content-location']);
            }

            response.statusCode = res.statusCode;
            response.send(fullBody);*/
        });
    });

    req.on('error', function (e) {
        console.log('[forward_http] problem with request: ' + e.message);

        response.statusCode = '404';
        response.send(url.parse(request.url).pathname + ' : ' + e.message);
    });

    console.log(JSON.stringify(request.body));

    // write data to request body
    if ((request.method.toLowerCase() == 'get') || (request.method.toLowerCase() == 'delete')) {
        req.write('');
    }
    else {
        req.write(request.body);
    }
    req.end();
}