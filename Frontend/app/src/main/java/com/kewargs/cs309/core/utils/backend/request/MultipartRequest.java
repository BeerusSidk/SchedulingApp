package com.kewargs.cs309.core.utils.backend.request;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Custom request class for handling multipart/form-data requests with Volley.
 * <p>
 * This class extends the Volley Request class, specifically handling the creation and transmission
 * of multipart requests. It is designed to send binary data like images, files, or other media
 * along with text data as part of a single request. It's commonly used for file uploads.
 *
 * @author Nhat Bui
 **/
public class MultipartRequest extends Request<String> {

    private final Response.Listener<String> mListener;
    private final Response.ErrorListener mErrorListener;
    private final String pdfName;
    private final byte[] pdfData;
    private final String mBoundary = "apiclient-" + System.currentTimeMillis();
    private final String mLineEnd = "\r\n";
    private final String mTwoHyphens = "--";

    private final String token;

    public MultipartRequest(
        int method,
        String url,
        String pdfName,
        byte[] pdfData,
        String token,
        Response.Listener<String> listener,
        Response.ErrorListener errorListener
    ) {
        super(method, url, errorListener);
        this.mListener = listener;
        this.mErrorListener = errorListener;
        this.pdfName = pdfName;
        this.pdfData = pdfData;
        this.token = token;
    }

    @Override
    public String getBodyContentType() {
        return "multipart/form-data;boundary=" + mBoundary;
    }

    @Override
    public Map<String, String> getHeaders() {
        return new HashMap<>() {{
            put("Authorization", "Bearer " + token);
        }};
    }

    @Override
    public byte[] getBody() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);

        try {
            dos.writeBytes(mTwoHyphens + mBoundary + mLineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"" + "file" + "\"; filename=\"" + pdfName + "\"" + mLineEnd);
            dos.writeBytes("Content-Type: application/pdf" + mLineEnd);
            dos.writeBytes(mLineEnd);
            dos.write(pdfData);
            dos.writeBytes(mLineEnd);
            dos.writeBytes(mTwoHyphens + mBoundary + mTwoHyphens + mLineEnd);

            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String responseString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }

    @Override
    public void deliverError(com.android.volley.VolleyError error) {
        System.out.println(error.toString());
        mErrorListener.onErrorResponse(error);
    }
}
