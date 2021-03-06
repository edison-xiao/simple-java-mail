package org.simplejavamail.email;

import org.junit.Before;
import org.junit.Test;
import org.simplejavamail.util.ConfigLoader;

import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.util.ByteArrayDataSource;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

public class EmailBuilderTest {

	@Before
	public void setup() {
		// clear defaults to get accurate results as set in the tests
		ConfigLoader.loadProperties(new Properties(), false);
	}

	@Test
	public void testBuilderFromAddress() {
		final Email email = new EmailBuilder()
				.from(new Recipient("lollypop", "lol.pop@somemail.com", null))
				.build();

		assertThat(email.getFromRecipient().getName()).isEqualTo("lollypop");
		assertThat(email.getFromRecipient().getAddress()).isEqualTo("lol.pop@somemail.com");
		assertThat(email.getFromRecipient().getType()).isNull();
	}

	@Test
	public void testBuilderFromAddressOverwriteWithAlternativeBuilderMethod() {
		final Email email = new EmailBuilder()
				.from("lollypop", "lol.pop@somemail.com") // should be overwritted
				.from(new Recipient("lollypop2", "lol.pop2@somemail.com", null))
				.build();

		assertThat(email.getFromRecipient().getName()).isEqualTo("lollypop2");
		assertThat(email.getFromRecipient().getAddress()).isEqualTo("lol.pop2@somemail.com");
		assertThat(email.getFromRecipient().getType()).isNull();
	}

	@Test
	public void testBuilderReplyToAddress() {
		final Email email = new EmailBuilder()
				.replyTo(new Recipient("lollypop", "lol.pop@somemail.com", null))
				.build();

		assertThat(email.getReplyToRecipient().getName()).isEqualTo("lollypop");
		assertThat(email.getReplyToRecipient().getAddress()).isEqualTo("lol.pop@somemail.com");
		assertThat(email.getReplyToRecipient().getType()).isNull();
	}

	@Test
	public void testBuilderReplyToAddressOverwriteWithAlternativeBuilderMethod() {
		final Email email = new EmailBuilder()
				.replyTo("lollypop", "lol.pop@somemail.com") // should be overwritted
				.replyTo(new Recipient("lollypop2", "lol.pop2@somemail.com", null))
				.build();

		assertThat(email.getReplyToRecipient().getName()).isEqualTo("lollypop2");
		assertThat(email.getReplyToRecipient().getAddress()).isEqualTo("lol.pop2@somemail.com");
		assertThat(email.getReplyToRecipient().getType()).isNull();
	}

	@Test
	public void testBuilderToAddresses() {
		final Email email = new EmailBuilder()
				.to("1", "1@candyshop.org")
				.to(null, "2@candyshop.org")
				.to(new Recipient("3", "3@candyshop.org", null))
				.to(new Recipient(null, "4@candyshop.org", null))
				.to("5@candyshop.org")
				.to("6@candyshop.org,7@candyshop.org")
				.to("8@candyshop.org;9@candyshop.org")
				.to("10@candyshop.org;11@candyshop.org,12@candyshop.org")
				.to(new Recipient("13", "13@candyshop.org", null), new Recipient("14", "14@candyshop.org", null))
				.build();

		assertThat(email.getRecipients()).containsExactlyInAnyOrder(
				createRecipient("1", "1@candyshop.org", Message.RecipientType.TO),
				createRecipient(null, "2@candyshop.org", Message.RecipientType.TO),
				createRecipient("3", "3@candyshop.org", Message.RecipientType.TO),
				createRecipient(null, "4@candyshop.org", Message.RecipientType.TO),
				createRecipient(null, "5@candyshop.org", Message.RecipientType.TO),
				createRecipient(null, "6@candyshop.org", Message.RecipientType.TO),
				createRecipient(null, "7@candyshop.org", Message.RecipientType.TO),
				createRecipient(null, "8@candyshop.org", Message.RecipientType.TO),
				createRecipient(null, "9@candyshop.org", Message.RecipientType.TO),
				createRecipient(null, "10@candyshop.org", Message.RecipientType.TO),
				createRecipient(null, "11@candyshop.org", Message.RecipientType.TO),
				createRecipient(null, "12@candyshop.org", Message.RecipientType.TO),
				createRecipient("13", "13@candyshop.org", Message.RecipientType.TO),
				createRecipient("14", "14@candyshop.org", Message.RecipientType.TO)
		);
	}

	@Test
	public void testBuilderCCAddresses() {
		final Email email = new EmailBuilder()
				.cc("1", "1@candyshop.org")
				.cc(null, "2@candyshop.org")
				.cc(new Recipient("3", "3@candyshop.org", null))
				.cc(new Recipient(null, "4@candyshop.org", null))
				.cc("5@candyshop.org")
				.cc("6@candyshop.org,7@candyshop.org")
				.cc("8@candyshop.org;9@candyshop.org")
				.cc("10@candyshop.org;11@candyshop.org,12@candyshop.org")
				.cc(new Recipient("13", "13@candyshop.org", null), new Recipient("14", "14@candyshop.org", null))
				.build();

		assertThat(email.getRecipients()).containsExactlyInAnyOrder(
				createRecipient("1", "1@candyshop.org", Message.RecipientType.CC),
				createRecipient(null, "2@candyshop.org", Message.RecipientType.CC),
				createRecipient("3", "3@candyshop.org", Message.RecipientType.CC),
				createRecipient(null, "4@candyshop.org", Message.RecipientType.CC),
				createRecipient(null, "5@candyshop.org", Message.RecipientType.CC),
				createRecipient(null, "6@candyshop.org", Message.RecipientType.CC),
				createRecipient(null, "7@candyshop.org", Message.RecipientType.CC),
				createRecipient(null, "8@candyshop.org", Message.RecipientType.CC),
				createRecipient(null, "9@candyshop.org", Message.RecipientType.CC),
				createRecipient(null, "10@candyshop.org", Message.RecipientType.CC),
				createRecipient(null, "11@candyshop.org", Message.RecipientType.CC),
				createRecipient(null, "12@candyshop.org", Message.RecipientType.CC),
				createRecipient("13", "13@candyshop.org", Message.RecipientType.CC),
				createRecipient("14", "14@candyshop.org", Message.RecipientType.CC)
		);
	}

	@Test
	public void testBuilderBCCAddresses() {
		final Email email = new EmailBuilder()
				.bcc("1", "1@candyshop.org")
				.bcc(null, "2@candyshop.org")
				.bcc(new Recipient("3", "3@candyshop.org", null))
				.bcc(new Recipient(null, "4@candyshop.org", null))
				.bcc("5@candyshop.org")
				.bcc("6@candyshop.org,7@candyshop.org")
				.bcc("8@candyshop.org;9@candyshop.org")
				.bcc("10@candyshop.org;11@candyshop.org,12@candyshop.org")
				.bcc(new Recipient("13", "13@candyshop.org", null), new Recipient("14", "14@candyshop.org", null))
				.build();

		assertThat(email.getRecipients()).containsExactlyInAnyOrder(
				createRecipient("1", "1@candyshop.org", Message.RecipientType.BCC),
				createRecipient(null, "2@candyshop.org", Message.RecipientType.BCC),
				createRecipient("3", "3@candyshop.org", Message.RecipientType.BCC),
				createRecipient(null, "4@candyshop.org", Message.RecipientType.BCC),
				createRecipient(null, "5@candyshop.org", Message.RecipientType.BCC),
				createRecipient(null, "6@candyshop.org", Message.RecipientType.BCC),
				createRecipient(null, "7@candyshop.org", Message.RecipientType.BCC),
				createRecipient(null, "8@candyshop.org", Message.RecipientType.BCC),
				createRecipient(null, "9@candyshop.org", Message.RecipientType.BCC),
				createRecipient(null, "10@candyshop.org", Message.RecipientType.BCC),
				createRecipient(null, "11@candyshop.org", Message.RecipientType.BCC),
				createRecipient(null, "12@candyshop.org", Message.RecipientType.BCC),
				createRecipient("13", "13@candyshop.org", Message.RecipientType.BCC),
				createRecipient("14", "14@candyshop.org", Message.RecipientType.BCC)
		);
	}

	@Test
	public void testBuilderEmbeddingImages() {
		final EmailBuilder builder = new EmailBuilder()
				.embedImage("a", new ByteArrayDataSource(new byte[3], ""))
				.embedImage(null, new DataSourceWithDummyName())
				.embedImage("a", new byte[3], "mimetype");
		try {
			builder.embedImage(null, new ByteArrayDataSource(new byte[3], ""));
			failBecauseExceptionWasNotThrown(EmailException.class);
		} catch (EmailException e) {
			// ok
		}
		try {
			builder.embedImage(null, new byte[3], "mimetype");
			failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
		} catch (IllegalArgumentException e) {
			// ok
		}
	}

	@Test
	public void testBuilderAddingAttachmentsWithMissingNameWithoutExceptions() {
		new EmailBuilder()
				.addAttachment("a", new ByteArrayDataSource(new byte[3], "text/txt"))
				.addAttachment(null, new DataSourceWithDummyName())
				.addAttachment("a", new byte[3], "text/txt")
				.addAttachment(null, new ByteArrayDataSource(new byte[3], "text/txt"))
				.addAttachment(null, new byte[3], "text/txt");
		// ok no exceptions
	}

	private Recipient createRecipient(final String name, final String emailAddress, final Message.RecipientType recipientType) {
		return new Recipient(name, emailAddress, recipientType);
	}

	private static class DataSourceWithDummyName implements DataSource {
		@Override
		public InputStream getInputStream() {
			return null;
		}

		@Override
		public OutputStream getOutputStream() {
			return null;
		}

		@Override
		public String getContentType() {
			return "text/txt";
		}

		@Override
		public String getName() {
			return "dummy";
		}
	}
}