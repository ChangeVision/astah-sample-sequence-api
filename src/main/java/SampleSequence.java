import java.io.InputStream;

import com.change_vision.jude.api.inf.AstahAPI;
import com.change_vision.jude.api.inf.model.IClass;
import com.change_vision.jude.api.inf.model.IGate;
import com.change_vision.jude.api.inf.model.IInteraction;
import com.change_vision.jude.api.inf.model.ILifeline;
import com.change_vision.jude.api.inf.model.IMessage;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.model.ISequenceDiagram;
import com.change_vision.jude.api.inf.project.ModelFinder;
import com.change_vision.jude.api.inf.project.ProjectAccessor;


public class SampleSequence {
	
	public static void main(String[] args) throws Exception {
		AstahAPI api = AstahAPI.getAstahAPI();
		ProjectAccessor projectAccessor = api.getProjectAccessor();
		try {
			openSampleModel(projectAccessor);
			INamedElement[] foundElements = findSequence(projectAccessor);
			for (INamedElement element : foundElements) {
				ISequenceDiagram sequence = castSequence(element);
				if (sequence == null) {
					continue;
				}
				IInteraction interaction = sequence.getInteraction();
				showInteraction(interaction);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			projectAccessor.close();
		}
	}

	/**
	 * サンプルモデルを開きます。
	 * @param projectAccessor
	 * @throws Exception
	 */
	private static void openSampleModel(ProjectAccessor projectAccessor)
			throws Exception {
		InputStream astahFileStream = SampleSequence.class.getResourceAsStream("Sample.asta");
		projectAccessor.open(astahFileStream);
	}

	/**
	 * Sequence Diagramという名前の要素を取得します。
	 * @param projectAccessor
	 * @return 発見したモデル
	 * @throws Exception
	 */
	private static INamedElement[] findSequence(
			ProjectAccessor projectAccessor) throws Exception {
		INamedElement[] foundElements = projectAccessor.findElements(new ModelFinder() {
			public boolean isTarget(INamedElement namedElement) {
				return namedElement.getName().equals("Sequence Diagram");
			}
		});
		return foundElements;
	}

	private static ISequenceDiagram castSequence(
			INamedElement element) {
		ISequenceDiagram sequenceDiagram = null;
		if (element instanceof ISequenceDiagram) {
			sequenceDiagram = (ISequenceDiagram) element;
		}
		return sequenceDiagram;
	}

	/**
	 * 相互作用(IInteraction)を表示します。
	 * @param interaction
	 * @see http://members.change-vision.com/javadoc/astah-api/6_7_0-43495/api/ja/doc/javadoc/com/change_vision/jude/api/inf/model/IInteraction.html
	 */
	private static void showInteraction(IInteraction interaction) {
		System.out.println("start ");
		showSeparator();
		showGates(interaction);
		showSeparator();
		showLifelines(interaction);
		showSeparator();
		showMessages(interaction);
		showSeparator();
		System.out.println("end.");
	}

	private static void showGates(IInteraction interaction) {
		System.out.println("Gate start.");
		IGate[] gates = interaction.getGates();
		for (IGate gate : gates) {
			showGate(gate);
		}
		System.out.println("Gate end.");
		
	}

	/**
	 * ゲートを表示します。
	 * @param gate
	 * @see http://members.change-vision.com/javadoc/astah-api/6_7_0-43495/api/ja/doc/javadoc/com/change_vision/jude/api/inf/model/IGate.html
	 */
	private static void showGate(IGate gate) {
		System.out.println(gate);
	}

	private static void showLifelines(IInteraction interaction) {
		System.out.println("Lifeline start.");
		ILifeline[] lifelines = interaction.getLifelines();
		boolean first = true;
		for (ILifeline lifeline : lifelines) {
			if (!first) showMiniSeparator();
			showLifeline(lifeline);
			first = false;
		}
		System.out.println("Lifeline end.");
	}

	/**
	 * ライフラインを表示します。
	 * @param lifeline
	 * @see http://members.change-vision.com/javadoc/astah-api/6_7_0-43495/api/ja/doc/javadoc/com/change_vision/jude/api/inf/model/ILifeline.html
	 */
	private static void showLifeline(ILifeline lifeline) {
		System.out.println("Lifeline : " + lifeline);
		showBase(lifeline);
		showFragments(lifeline);
	}

	private static void showBase(ILifeline lifeline) {
		IClass base = lifeline.getBase();
		if (base != null) {
			System.out.println("Base : " + base);
		}
	}

	private static void showFragments(ILifeline lifeline) {
		showMiniSeparator();
		System.out.println("Fragment start.");
		INamedElement[] fragments = lifeline.getFragments();
		for (INamedElement fragment : fragments) {
			System.out.println(fragment);
		}
		System.out.println("Fragment end.");
		showMiniSeparator();
	}

	private static void showMessages(IInteraction interaction) {
		System.out.println("Message start.");
		IMessage[] messages = interaction.getMessages();
		boolean first = true;
		for (IMessage message : messages) {
			if (!first) showMiniSeparator();
			showMessage(message);
			first = false;
		}
		showMiniSeparator();
		System.out.println("Message end.");
	}

	/**
	 * メッセージを表示します。
	 * @param message
	 * @see http://members.change-vision.com/javadoc/astah-api/6_7_0-43495/api/ja/doc/javadoc/com/change_vision/jude/api/inf/model/IMessage.html
	 */
	private static void showMessage(IMessage message) {
		System.out.println("message : " + message);
		INamedElement source = message.getSource();
		System.out.println("source : " + source);
		INamedElement target = message.getTarget();
		System.out.println("target : " + target);
	}

	private static void showMiniSeparator() {
		System.out.println("----");
	}

	private static void showSeparator() {
		System.out.println("-----------------------");
	}

}
