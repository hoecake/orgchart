/**
 * Copyright © 2012 ITD Systems
 *
 * This file is part of Alvex
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.alvexcore.repo;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.security.Key;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
 
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.PropertyPlaceholderHelper;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


import javax.xml.crypto.AlgorithmMethod;
import javax.xml.crypto.KeySelector;
import javax.xml.crypto.KeySelectorException;
import javax.xml.crypto.KeySelectorResult;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.Properties;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.model.ContentModel;
import org.alfresco.repo.model.Repository;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.alfresco.repo.tenant.TenantAdminService;
import org.alfresco.repo.tenant.TenantDeployer;
import org.alfresco.repo.tenant.TenantService;
import org.alfresco.repo.tenant.TenantUtil;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.namespace.QName;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationEvent;
import org.springframework.extensions.surf.util.AbstractLifecycleBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alvexcore.license.LicenseInfo;
import com.alvexcore.license.LicenseStatus;

/*
 * Repository extension registry implementation
 *
 *
 */

class SimpleKeySelectorResult implements KeySelectorResult {
	private PublicKey pk;

	SimpleKeySelectorResult(PublicKey pk) {
		this.pk = pk;
	}

	public Key getKey() {
		return pk;
	}
}

class AlvexKeySelector extends KeySelector {

	public KeySelectorResult select(KeyInfo keyInfo,
			Purpose purpose, AlgorithmMethod method,
			XMLCryptoContext context) throws KeySelectorException {
		return new SimpleKeySelectorResult(new PublicKey() {

			@Override
			public String getAlgorithm() {
				return "RSA";
			}

			@Override
			public String getFormat() {
				return "X.509";
			}

			@Override
			public byte[] getEncoded() {
				return new byte[] { 48, -126, 1, 34, 48, 13, 6, 9, 42, -122,
						72, -122, -9, 13, 1, 1, 1, 5, 0, 3, -126, 1, 15, 0, 48,
						-126, 1, 10, 2, -126, 1, 1, 0, -115, 117, -12, -114,
						-121, -128, 76, 99, -114, -37, 107, -44, 108, 36, 38,
						99, 13, -93, -73, -62, 44, 10, 120, -22, -31, -25,
						-109, 45, 24, -47, 59, -87, -39, -29, -35, -96, 13,
						-117, 31, -98, 107, 80, -104, -72, 5, -32, 79, -115,
						59, -87, 109, -121, 104, 36, -14, 123, -113, 87, -50,
						40, -52, -59, -52, -7, -13, -34, 17, -29, -39, 63, -62,
						-44, 51, 68, -98, -115, -13, 10, -7, -101, 81, -72, 81,
						91, -94, 91, -94, 6, 65, 84, 35, -121, 14, -103, 38, 6,
						59, 115, -110, 4, -63, -89, -22, 27, 126, -96, -32, 97,
						105, -108, 14, -23, -62, -89, -41, 30, -126, -114, 121,
						17, 125, 18, 124, -114, 0, -13, 85, -11, 92, 87, -16,
						3, 30, 23, -126, -33, 122, 126, -72, -95, 29, 73, -24,
						-34, -27, -41, 109, -77, -108, -34, 91, -36, -3, 112,
						13, 30, 111, 9, -105, 7, 8, -70, 95, -128, -82, -13,
						-4, 127, -58, 68, -114, 89, 69, 101, -106, -123, -36,
						-90, -110, -44, 45, 25, 107, 52, 6, 69, -35, 89, 7,
						-59, 96, 4, 97, 29, 24, -50, -59, -40, 104, 70, 68,
						-28, 77, 94, -57, -38, 91, -99, 37, -89, 105, -126, 52,
						80, 111, 107, -69, 22, 39, -70, -5, 87, -33, -77, -79,
						-64, 76, -12, -58, -37, 56, 102, 17, 59, 11, -73, -68,
						-96, -108, -47, 13, -113, -77, 60, 88, -128, 19, -42,
						12, 49, 89, 7, -11, -11, -87, 37, 2, 3, 1, 0, 1 };
			}
		});
	}
}

public class RepositoryExtensionRegistry extends AbstractLifecycleBean implements TenantDeployer, InitializingBean {

	public static final String EDITION_CE = "Community";
	public static final String EDITION_EE = "Enterprise";
	public static final String ANY_VERSION = "ANY";
	
	private static Log logger = LogFactory.getLog(RepositoryExtensionRegistry.class);
	
	final static QName[] ALVEX_PATH = { AlvexContentModel.ASSOC_NAME_SYSTEM,
			AlvexContentModel.ASSOC_NAME_ALVEX };
	
	private Repository repository = null;
	private ServiceRegistry serviceRegistry = null;
	private TenantService tenantService = null;
	private TenantAdminService tenantAdminService = null;
	private LicenseInfo licenseInfo = null;
	
	private String version;
	private String edition;
	private String codename;

	final private String PROP_VERSION = "alvex.version";
	final private String PROP_EDITION = "alvex.edition";
	final private String PROP_CODENAME = "alvex.codename";

	final private String DEV_VERSION = "dev";
	final private String DEV_EDITION = "dev";
	final private String DEV_CODENAME = "dev";

	public String getVersion() {
		return version;
	}

	public String getEdition() {
		return edition;
	}

	// list of extensions
	protected List<RepositoryExtension> extensions = new ArrayList<RepositoryExtension>();

	public Repository getRepository() {
		return repository;
	}

	public ServiceRegistry getServiceRegistry() {
		return serviceRegistry;
	}

	@Required
	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	@Required
	public void setServiceRegistry(ServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}

	public String getCodename() {
		return codename;
	}

	protected void initAlvex() throws Exception {
		InputStream is = this.getClass().getClassLoader()
				.getResourceAsStream("alvex-release.properties");
		if (is != null) {
			Properties props = new Properties();
			props.load(is);
			version = (props.getProperty(PROP_VERSION) != null) ? props
					.getProperty(PROP_VERSION) : DEV_VERSION;
			edition = (props.getProperty(PROP_EDITION) != null) ? props
					.getProperty(PROP_EDITION) : DEV_EDITION;
			codename = (props.getProperty(PROP_CODENAME) != null) ? props
					.getProperty(PROP_CODENAME) : DEV_CODENAME;
		} else {
			version = DEV_VERSION;
			edition = DEV_EDITION;
			codename = DEV_CODENAME;
		}

		initExtensions();
	}
	
	public void initExtensions() throws Exception {
		AuthenticationUtil.runAsSystem(new RunAsWork<Void>() {
			@Override
			public Void doWork() throws Exception {
				loadLicense();
				initContainer();
				for (RepositoryExtension ext : extensions)
					ext.init(false);	
				return null;
			}
		});
		checkLicense();
	}

	public void checkLicense() throws Exception {
		LicenseStatus status = getLicenseStatus();
		if( !status.getValid() )
			throw new Exception( status.getReason() );
	}

	protected void dropExtensionsCache() {
		for (RepositoryExtension ext: extensions)
			ext.dropNodeCache();
	}

	private NodeRef initContainer() throws Exception {
		NodeRef node = createPath(ALVEX_PATH, null, null);
		NodeService nodeService = serviceRegistry.getNodeService();
		nodeService.addAspect(node, ContentModel.ASPECT_AUDITABLE, null);
		return node;
	}

	// creates containers specified by assocs
	public NodeRef createPath(QName[] path, QName[] assocs, QName[] types)
			throws Exception {
		if (path == null || path.length == 0)
			throw new Exception("Path cannot be null or empty");
		if (assocs != null && path.length != assocs.length)
			throw new Exception("Size of path and assocs must be equal");
		if (types != null && path.length != types.length)
			throw new Exception("Size of path and types must be equal");
		NodeService nodeService = serviceRegistry.getNodeService();
		NodeRef node = nodeService
				.getRootNode(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE);
		for (int i = 0; i < path.length; i++) {
			QName assocQName = path[i];
			QName childAssoc = assocs == null ? ContentModel.ASSOC_CHILDREN
					: assocs[i];
			QName childType = types == null ? ContentModel.TYPE_CONTAINER
					: types[i];
			List<ChildAssociationRef> childAssocs = nodeService.getChildAssocs(
					node, childAssoc, assocQName);
			if (childAssocs.size() == 0) {
				// create new node
				node = nodeService.createNode(node, childAssoc, assocQName,
						childType).getChildRef();
			} else if (childAssocs.size() == 1)
				// get ref of existent node
				node = childAssocs.get(0).getChildRef();
		}
		return node;
	}

	private File findLicenseFile() {
		PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper("${",
				"}");
		String sharedLoader = System.getProperty("shared.loader");
		if (sharedLoader == null)
			return null;
		String sharedLoadPaths = helper.replacePlaceholders(sharedLoader,
				System.getProperties());
		for (String path : sharedLoadPaths.split(",")) { // FIXME is this separator the same for all envs?
			File file = new File(path, "alfresco" + File.separator
					+ "extension" + File.separator + "license");
			if (file.isDirectory())
				for (File elem : file.listFiles())
					if (elem.getAbsolutePath().endsWith("alvex.lic"))
						return elem;
		}
		return null;
	}

	private void loadLicense() throws Exception {
		File licenseFile = findLicenseFile();
		if (licenseFile == null) {
			NodeService nodeService = serviceRegistry.getNodeService();
			NodeRef node = initContainer();
			Date created = (Date) nodeService.getProperty(node,
					ContentModel.PROP_CREATED);
			Calendar cal = Calendar.getInstance();
			cal.setTime(created);
			cal.add(Calendar.DATE, 60);
			licenseInfo = new LicenseInfo("TRIAL", "ITD Systems", "Alvex",
					EDITION_EE, ANY_VERSION, -1, -1, created, cal.getTime(), true);
		} else {
			InputStream is = new FileInputStream(licenseFile);
			licenseInfo = getLicenseInfo(is);
		}
	}

	private LicenseInfo getLicenseInfo(InputStream lic) {
		Document licenseXML = null;
		try {
			DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
			fact.setNamespaceAware(true);
			licenseXML = fact.newDocumentBuilder().parse(lic);
			NodeList nl = licenseXML.getElementsByTagNameNS(XMLSignature.XMLNS,
					"Signature");
			DOMValidateContext valContext = new DOMValidateContext(
					new AlvexKeySelector(), nl.item(0));
			XMLSignatureFactory sfac = XMLSignatureFactory.getInstance("DOM");
			XMLSignature sgn = sfac.unmarshalXMLSignature(valContext);
			if (!sgn.validate(valContext))
				return LicenseInfo.INVALID_LICENSE;
		} catch (Exception ex) {
			return LicenseInfo.INVALID_LICENSE;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String id = licenseXML.getDocumentElement().getElementsByTagName("id")
				.item(0).getTextContent();
		String product = licenseXML.getDocumentElement()
				.getElementsByTagName("product").item(0).getTextContent();
		String owner = licenseXML.getDocumentElement()
				.getElementsByTagName("owner").item(0).getTextContent();
		String edition = licenseXML.getDocumentElement()
				.getElementsByTagName("edition").item(0).getTextContent();
		
		// We intentially have separate try/catch blocks. These tags may fail independently
		// and we'd like to prevent failed version tag from stopping dates parsing.
		String version = ANY_VERSION;
		try {
			version = licenseXML.getDocumentElement()
					.getElementsByTagName("version").item(0).getTextContent();
		} catch (Exception e) {
		}
		
		Date issued = null;
		Date validThru = null;
		try {
			String expiresStr = licenseXML.getDocumentElement()
					.getElementsByTagName("expires").item(0).getTextContent();
			validThru = sdf.parse(expiresStr);
			String issuedStr = licenseXML.getDocumentElement()
					.getElementsByTagName("issued").item(0).getTextContent();
			issued = sdf.parse(issuedStr);
		} catch (Exception e) {
			String expiresStr = licenseXML.getDocumentElement()
					.getElementsByTagName("expires").item(0).getTextContent();
			String issuedStr = licenseXML.getDocumentElement()
					.getElementsByTagName("issued").item(0).getTextContent();
			logger.warn("Can not parse license dates. " 
					+ "Issued: " + issuedStr + ". Expires: " + expiresStr + ".");
		}

		int cores = new Integer(licenseXML.getDocumentElement()
				.getElementsByTagName("cores").item(0).getTextContent());
		int users = new Integer(licenseXML.getDocumentElement()
				.getElementsByTagName("users").item(0).getTextContent());

		return new LicenseInfo(id, owner, product, edition, version, 
				cores, users, issued, validThru, false);
	}

	// resolved container specified by assocs
	public NodeRef resolvePath(QName[] path, QName[] assocs) {
		if (path == null || path.length == 0)
			throw new AlfrescoRuntimeException("Path cannot be null or empty");
		if (assocs != null && path.length != assocs.length)
			throw new AlfrescoRuntimeException("Size of path and assocs must be equal");
		NodeService nodeService = serviceRegistry.getNodeService();
		NodeRef node = repository.getRootHome();
		
		for (int i = 0; i < path.length; i++) {
			QName assocQName = path[i];
			QName childAssoc = assocs == null ? ContentModel.ASSOC_CHILDREN
					: assocs[i];
			List<ChildAssociationRef> childAssocs = nodeService.getChildAssocs(
					node, childAssoc, assocQName);
			if (childAssocs.size() == 0) {
				return null;
			} else if (childAssocs.size() == 1)
				// get ref of existent node
				node = childAssocs.get(0).getChildRef();
		}
		return node;		
	}

	public String getSystemId() {
		return repository.getCompanyHome().getId();
	}

	public int getServerCores() {
		return Runtime.getRuntime().availableProcessors();
	}

	// TODO: do not count disabled users
	public long getRegisteredUsers() {
		long users = AuthenticationUtil.runAsSystem(new RunAsWork<Long>() {
			@Override
			public Long doWork() throws Exception {
				return new Long (serviceRegistry.getAuthorityService().countUsers());
			}
		});
		return users - 3; /* '-3' stands for admin, guest, System */
	}

	public LicenseInfo getLicenseInfo() {
		return licenseInfo;
	}
	
	public LicenseStatus getLicenseStatus() {
		
		// Check license validity.
		// It also covers broken signature because of LicenseInfo.INVALID_LICENSE structure
		if( licenseInfo.getCores() <= 0 
				&& licenseInfo.getUsers() <= 0 
				&& ! licenseInfo.getTrial() )
		{
			return new LicenseStatus(false, "Alvex license file is not consistent");
		}
		
		// Check CPU cores
		int cores = getServerCores();
		if (licenseInfo.getCores() < cores && licenseInfo.getCores() > 0)
		{
			return new LicenseStatus(false, "CPU limit for Alvex installation exceeded. " 
						+ "Licensed cores: " + licenseInfo.getCores() + ". " 
						+ "Actual cores: " + cores + ". ");
		}
		
		// Check users
		long users = getRegisteredUsers();
		// Warn if the limit is exceeded but we are still within 25% tolerance
		if (users > licenseInfo.getUsers() && licenseInfo.getUsers() > 0
				&& users <= licenseInfo.getUsers() * 1.25)
		{
			logger.warn("User limit for Alvex installation exceeded. " 
						+ "Licensed users: " + licenseInfo.getUsers() + ". " 
						+ "Actual users: " + users + ". ");
		}
		// Die if we are out of 25% tolerance
		if (users > licenseInfo.getUsers() * 1.25 && licenseInfo.getUsers() > 0)
		{
			return new LicenseStatus(false, "User limit for Alvex installation exceeded. " 
						+ "Licensed users: " + licenseInfo.getUsers() + ". " 
						+ "Actual users: " + users + ". ");
		}
		
		// Check expiry date
		// (Skip this check if Alvex version is not ANY - it means perpetual license.)
		if (licenseInfo.getValidThru().before(Calendar.getInstance().getTime())
				&& licenseInfo.getVersion().equalsIgnoreCase(ANY_VERSION) )
		{
			return new LicenseStatus(false, "Alvex license has expired");
		}
		
		// Check version match for perpetual licenses
		if( ! licenseInfo.getVersion().equalsIgnoreCase(ANY_VERSION) 
				&& ! licenseInfo.getVersion().equalsIgnoreCase(version) )
		{
			return new LicenseStatus(false, "Alvex license version mismatch. " 
						+ "Licensed version: " + licenseInfo.getVersion() + ". " 
						+ "Actual version: " + version + ". ");
		}
		
		return new LicenseStatus(true, "");
	}
	
	// registers new extension
	public void registerExtension(RepositoryExtension extension)
			throws Exception {
		for (RepositoryExtension ext : extensions)
			if (ext.getId().equals(extension.getId()))
				throw new Exception("Extension " + ext.getId()
						+ " is registered already");
		// register if it's not registered yet
		extensions.add(extension);
	}

	// returns a list of installed extensions
	public List<RepositoryExtension> getInstalledExtensions() {
		return extensions;
	}

	@Override
	protected void onBootstrap(ApplicationEvent event) {
		try {
			initAlvex();
		} catch (Exception e) {
			throw new AlfrescoRuntimeException("Alvex initialization failed", e);
		}
	}

	@Override
	protected void onShutdown(ApplicationEvent event) {
	}
	
	public RepositoryExtension getExtension(String id) throws Exception{
		for (RepositoryExtension ex: extensions)
			if (ex.getId().equals(id))
				return ex;
		throw new Exception("Extension not found");
	}

	public TenantService getTenantService() {
		return tenantService;
	}

	@Required
	public void setTenantService(TenantService tenantService) {
		this.tenantService = tenantService;
	}

	@Override
	public void onEnableTenant() {
		try {
			initExtensions();
		} catch (Exception e) {
			throw new AlfrescoRuntimeException("Alvex init for tenant failed", e);
		}
		
	}

	@Override
	public void onDisableTenant() {
		dropExtensionsCache();
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init() {
	}

	public TenantAdminService getTenantAdminService() {
		return tenantAdminService;
	}

	public void setTenantAdminService(TenantAdminService tenantAdminService) {
		this.tenantAdminService = tenantAdminService;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		tenantAdminService.register(this);		
	}
}
