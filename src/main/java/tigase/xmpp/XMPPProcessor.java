/*
 * Tigase Jabber/XMPP Server
 * Copyright (C) 2004-2007 "Artur Hefczyc" <artur.hefczyc@tigase.org>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. Look for COPYING file in the top folder.
 * If not, see http://www.gnu.org/licenses/.
 *
 * $Rev$
 * Last modified by $Author$
 * $Date$
 */

package tigase.xmpp;

import tigase.xml.Element;
import tigase.db.UserRepository;
import tigase.db.TigaseDBException;

/**
 * <code>XMPPProcessor</code> abstract class contains basic definition for
 * <em>XMPP</em> processor.
 * To create new processor implementing particular <em>XMPP</em> functionality
 * it is enough to extend this class and implement one abstract method.<br/>
 * Additionally to allow system properly recognize this processor you need also
 * to implement own constructor which sets proper values to parent constructor.
 * You must implement exactly one constructor with zero parameters which calls
 * parent constructor with proper values. Refer to constructor documentation
 * for information about required parameters.<br/>
 * To fully interact with entity connected to the session or with other entities
 * in <em>XMPP</em> network you should be also familiar with
 * <code>addReply(...)</code>, <code>addMessage(...)</code> and
 * <code>addBroadcast(...)</code> methods.<br/>
 * There is also partialy implemented functionality to send messages to entities
 * in other networks like <em>SMTP</em> or other implemented by the server.
 * Once this implementation is finished there will be more information available.
 * If you, however, are interested in this particular feature send a question
 * to author.
 *
 * <p>
 * Created: Tue Oct  5 20:31:23 2004
 * </p>
 * @author <a href="mailto:artur.hefczyc@tigase.org">Artur Hefczyc</a>
 * @version $Rev$
 */
public abstract class XMPPProcessor
	implements XMPPImplIfc, Comparable<XMPPProcessor> {

	private XMPPProcessor inst = null;

	protected XMPPProcessor() {	inst = this; }

	@Override
	public String[] supElements() { return null; }

	@Override
  public String[] supNamespaces() { return null; }

	@Override
  public Element[] supStreamFeatures(final XMPPResourceConnection session)
	{ return null; }

	@Override
  public Element[] supDiscoFeatures(final XMPPResourceConnection session)
	{ return null; }

	@Override
  public boolean isSupporting(final String element, final String ns) {
    String[] impl_elements = supElements();
    String[] impl_xmlns = supNamespaces();
    if (impl_elements != null && impl_xmlns != null) {
      for (int i = 0; i < impl_elements.length && i < impl_xmlns.length; i++) {
				// ******   WARNING!!!! WARNING!!!!    *****
				// This is intentional reference comparison!
				// This method is called very, very often and it is also very expensive
				// therefore all XML element names and xmlns are created using
				// String.intern()
        if (impl_elements[i] == element && impl_xmlns[i] == ns) {
          return true;
        } // end of if (ELEMENTS[i].equals(element) && XMLNSS[i].equals(ns))
      } // end of for (int i = 0; i < ELEMENTS.length; i++)
    } // end of if (impl_elements != null && impl_xmlns != null)
    return false;
  }

	public XMPPProcessor getInstance() { return inst; }

	// Implementation of java.lang.Comparable

  /**
   * Method <code>compareTo</code> is used to perform
   *
   * @param proc an <code>XMPPProcessor</code> value
   * @return an <code>int</code> value
   */
	@Override
  public final int compareTo(final XMPPProcessor proc) {
    return
      getClass().getName().compareTo(proc.getClass().getName());
  }

	@Override
	public void init(UserRepository rep) throws TigaseDBException {}

}// XMPPProcessor
