package com.mygdx.java.utils;

import java.nio.charset.Charset;
import java.security.GeneralSecurityException;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.filter.logging.MdcInjectionFilter;
import org.apache.mina.filter.ssl.SslFilter;
 

import com.mygdx.java.common.ssl.BogusSslContextFactory;

public class IoFilterChainBuilderUtils {
 
	public static SslFilter getServerSslFilter()
			throws GeneralSecurityException {

		return new SslFilter(BogusSslContextFactory.getInstance(true));
	}

	public static SslFilter getClientSslFilter()
			throws GeneralSecurityException {
		SslFilter sslFilter = new SslFilter(
				BogusSslContextFactory.getInstance(false));
		sslFilter.setUseClientMode(true);
		
		return sslFilter;
	}

	public static void addServerSslFilter(DefaultIoFilterChainBuilder chain)
			throws GeneralSecurityException {
		if (chain == null) {
			return;
		}
		chain.addLast("sslFilter", getServerSslFilter());
		System.out.println("SSL Server ON");
	}

	public static void addClientSslFilter(DefaultIoFilterChainBuilder chain)
			throws GeneralSecurityException {
		if (chain == null) {
			return;
		}
		SslFilter sslFilter = getClientSslFilter();
		// sslFilter.setUseClientMode(true);
		chain.addFirst("sslFilter", sslFilter);
		System.out.println("SSL Client ON");

	}

	public static void addLogger(DefaultIoFilterChainBuilder chain) {
		if (chain == null) {
			return;
		}
		chain.addLast("logger", new LoggingFilter());
		System.out.println("Logging ON");
	}

	public static void addCodec(DefaultIoFilterChainBuilder chain) {
		if (chain == null) {
			return;
		}
		chain.addLast("codec", new ProtocolCodecFilter(
				new TextLineCodecFactory()));
		System.out.println("ProtocolCodecFilter ON");
	}

	public static void addCodecUtf8(DefaultIoFilterChainBuilder chain) {
		if (chain == null) {
			return;
		}
		chain.addLast("codec_utf8", new ProtocolCodecFilter(
				new TextLineCodecFactory(Charset.forName("utf-8"),
						LineDelimiter.UNIX, LineDelimiter.UNIX)));
		System.out.println("ProtocolCodecFilter ON");
	}

	public static void addMdcInjection(DefaultIoFilterChainBuilder chain) {
		if (chain == null) {
			return;
		}
		chain.addLast("mdc", new MdcInjectionFilter());
		System.out.println("MdcInjectionFilter ON");
	}

	public static void addObjectSerializationCodec(
			DefaultIoFilterChainBuilder chain) {
		ObjectSerializationCodecFactory factory = new ObjectSerializationCodecFactory();
		factory.setDecoderMaxObjectSize(Integer.MAX_VALUE);
		factory.setEncoderMaxObjectSize(Integer.MAX_VALUE);
		chain.addLast("ObjectSerializationCodec", new ProtocolCodecFilter(
				factory));
		System.out.println("ObjectSerializationCode ON");
	}

}
