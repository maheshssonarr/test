package com.sonar.numeroscope;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class Predictions extends Activity {

	private TextView textViewDestiny;
	private TextView textViewPsychic;
	private TextView textViewName;
	private TextView textViewLabelPsychic;
	private TextView textViewLabelDestiny;
	private TextView textViewLabelName;
	public static String[][] predictions = new String[4][10];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_predictions);
		// Show the Up button in the action bar.
		setupActionBar();

		predictions[1][0] = "";
		predictions[1][1] = "Sunny, energetic, radiant, confident, proud, self centered, goal oriented, socially active, leader vs follower, self actualized, authoritative. Can be cruel in their intensity, arrogant and ready to rule, yet on account of the regal qualities also a protector and provider. Known for lavish gifts, boldness, with good endurance.";
		predictions[1][2] = "Shy and sensitive, emotional but repressed, indecisive and moody, much like the waxing and waning of the moon, an off and on changeable nature. Fragile, reflective, poetic, artistic and romantic. Possessing natural healing gifts. A delicate nature and easily cold, insecure, dependent on help. Passionate and perceptive, peacemakers, diplomatic, always seeking harmony, truthful, tough fighters when provoked. Don't learn from their mistakes, forgive, forget and repeat lessons. Need a lot of privacy and time alone, need rest, easily addicted to sweets.";
		predictions[1][3] = "Dynamic, cheerful, skilled, inflated ego, critical, large of heart, creative, self expressive, artistic, religious, savor the limelight, love to minister and hold court, happy go lucky, outgoing, inspirational and uplifting. Say 'yes' to everything, and accomplish lots due to their optimistic nature. Have a 'verve' that motivates others. Scatter their talents easily. Good with romance, engaging. Love money, career success, jealous and gossipy. Love a challenge, the bigger the better. Competent, value traditions.";
		predictions[1][4] = "Powerful, builder, egocentric, unpredictable, rebellious and antagonistic. Brilliant and futuristic. Greedy and selfish yet generous. Impulsive, secretive, willing to work for a better world, utopian, difficult to understand and get close to. Loyal to those they love but a loner and can be abrupt and rude. Non-conforming, strong, hard to keep up with energetically. Very mental, hard to grasp, like relating through a smokescreen, evasive. Good sense of order, keen observation skills. Desire driven, courageous, survivor nature. Uniquely different.";
		predictions[1][5] = "Restless, busy, fickle minded, youthful, lover of stimuli, adventuresome, modern and progressive, creative, resourceful. Good at communicating, have a way with words, scheming, can sell anything, convincing. Like change and to conquer new frontiers, upbeat personality, gentle at heart. Good with their hands. Make friends easily, love distraction, multiple careers, many relationships, excel in the entertainment industry. Independent, curious, love change, not easy to go to depths in anything because they are excitement driven. Rolling stone. Vastly gifted and enterprising with resources.";
		predictions[1][6] = "Loving and caring, everybody's friend, charming and charismatic, beauty oriented. Balanced yet paradoxical in their self expression. Good ally and counselor, home-maker, nurturing, sympathetic, service oriented yet independent. Periodical bouts of luxury seeking. Artistic, attractive, admired, adored, posses refined taste, sexual. Suffer when separated from family yet have to endure this occasionally, pleasant personality, excellent taste, open minded and open hearted.";
		predictions[1][7] = "Vast, spacious, spiritual. Trouble with expressing their feelings and vulnerable to emotional explosions. Mystical, poetic, intuitive, may be psychic. Have self control and dignity, steady and reliable friends. Skeptical, torn between rational thinking and the call of the unconscious. Proud, investigative, philosophical. Prefer to work alone, opinionated, stubborn, cynical, perfectionist, unsure, escapist, vague. Difficult to be in partnership with.";
		predictions[1][8] = "Strong constitution, action motivated, serious, heavy, goal oriented, good judge of character, slow, business minded, materially focussed. Able to manage wealth, fame and fortune with skill and savvy, major ups and downs with fiances. Independent, radical revolutionary, problems with authorities, stubborn, frustrated by limitations. Intimidating, dare devil, ruthless, intense, lonely, dependable and solid. Do not scare or panic easily, responsible to their own law only. Prefer to work alone, accomplish much.";
		predictions[1][9] = "Impressive and charismatic presence, warm personality. Aggressive, assertive, challenging, emotional, can fly into a rage, volatile, anger easily, impatient, aristocratic demeanor. Better in dealing with troubles of the many than the problem of a single person. Militant, benevolent, unprejudiced. Long for love and approval. Able to synthesize and harmonize with all other vibrations. Sincere, postpone personal satisfaction for common goals, hard working, serves.";
		predictions[2][0] = "";
		predictions[2][1] = "Original, works smarter not harder. Self referencing - follow me and my cause. Enthusiastic, individualistic, able to enlist friends and helpers everywhere. Explorer and innovator, driven by worldly desires and success, determined to accomplish something noteworthy. Courageous and commanding, astute negotiator, self made leader, willing to be and work at the frontiers of life. Opinionated, stubborn and rigid. People react to them with attraction or aversion.";
		predictions[2][2] = "Charming, warm, kind, co-operative, private, mystical, tactful, easily hurt as very sensitive. Will withhold affection/contact and move away from stressful situations and problems, will not 'make war'. Need security and emotional support, dependent nature, looking for stability and comfort in relationships. Need solitude to balance, prefer calming companionship to lively dazzle. Spiritual, truth loving, excellent counselors as they care. Difficulty making instant commitments. Non-athletic yet agile.";
		predictions[2][3] = "Hardworking, agreeable yet unable to follow through on all their promises and can stress out. Confident, don't like their actions challenged. Overall positive disposition, communicate well but resist opposition. Always busy, cheerful, idealistic, religiously minded, philosophical, adventurous. Experience betrayal as they rush projects and relationships and have too many. Proud, extravagant, exaggerated sense of Self. Resilient, yet emotionally vulnerable, do not like to be alone, like to excel at all they do and are.";
		predictions[2][4] = "Highly intelligent, a mental orientation. Radical by nature. Can get organized and deliver when least expected. Chase the impossible dream, yearn for freedom, feel easily restricted. Persevering, helpful, community minded, yet very private, make anonymous donations to the unconventional causes. Unsuccessful in matters of love, difficulties with intimacy. Serious, know how to enjoy the good life yet somehow never satisfied and wanting more. Strong likes and dislikes, will support the opposition. Not security minded, doubting, lonely. Have substantial knowledge at their fingertips. Non sharing.";
		predictions[2][5] = "Lucky, soft and sensuous, non committal, loveto explore all of live. Intelligent, able to grasp process and access vast amounts of information and live many realities simultaneously. Easily bored, need variety, childlike at heart, trusting and innocent. Self employed, the need for freedom dominates all decisions, want to try everything and go everywhere. Need discipline and to practice tolerance and understanding tobe happy and fulfilled. Like to cruise through life, nothing too heavy, easy does it. Technologically adept, joyous and fun to be with.Always look younger than their age.";
		predictions[2][6] = "Family and home oriented, exude understanding and compassion. Connoisseur of luxury, in need of comfort, loving philosophy, fair minded, fine sense of justice. Artistic and beauty loving, generous, not necessarily logical with finances. Vulnerable to praise and criticism, responsible and willing to sacrifice for others, value friends and provide a safe port for them in times of need. Fulfilled in marriage and as parents. Emotional, idealistic and imaginative. May worry too much and suffer from chronic stomach problems. Will spend long periods of time alone.";
		predictions[2][7] = "Charming, lively, unpredictable, able to access subconscious information, a dreamer, sociable, sentimental, studious, analytical. Withdraw when emotionally troubled, critical of self and others. Intuitive, spiritual, want to spend time contemplating the deeper issues of life and love. Trouble expressing feelings, can appear cold and detached, lack trust and courage. May suffer from a troubled mind, lack of determination and resolve. Metaphysically oriented.";
		predictions[2][8] = "Struggle with opposition, delays, failures, humiliation. Exceptional endurance, persevering, ambitious, success driven, desire fame, and materially oriented. Want to deliver something major in life and manifest a legacy. Isolated from themselves and others. Get entangled with the law and substance abuse, prone to addictions and depression and extreme states of consciousness. Age prematurely, suicidal tendencies, easily feel unwanted, unloved, neglected and rejected. Argumentative, manipulative. Need to learn to harness their considerable powers for the good of all to be satisfied.";
		predictions[2][9] = "Humanitarian, warm, real, direct, responsive, responsible. Always busy, work constantly. Broadminded, compassionate, dramatic in emotional expression, proud, disciplined, self sacrificing, universalist philosophy, community orientation. Excellent teachers, socially active, ecologically conscious, accomplish lots. Give of themselves without wanting anything in return, incorruptible when evolved. Romantic yet love impersonally, enjoy music and the arts as personal reward. Kind and understanding, concerned for the overall advancement of society, liberated, wise, serve. ";
		predictions[3][0] = "";
		predictions[3][1] = "LEADER, will enjoy positive reinforcement from, and success within circle of family, friends and society.";
		predictions[3][2] = "TEACHER, will expand healing qualities and serve as a reflection of cosmic truth to all.";
		predictions[3][3] = "ARTIST, will infuse, inspire and pull society upward and forward through idealistic contributions.";
		predictions[3][4] = "BUILDER, will challenge all convention and create in various realities through radical approaches.";
		predictions[3][5] = "ENTERTAINER, will network, innovate and help transform mainstream concepts.";
		predictions[3][6] = "HOMEMAKER, will nurture and support, will create lavish environments.";
		predictions[3][7] = "MYSTIC, will become poetic, prolific and philosophical, will contemplate.";
		predictions[3][8] = "WORKER, will revolutionize society and manifest thru fame and fortune.";
		predictions[3][9] = "HUMANIST, will love unconditionally and work ceaselessly.";

		textViewLabelPsychic = (TextView) findViewById(R.id.textViewLabelPsychic);
		textViewPsychic = (TextView) findViewById(R.id.textViewPsychic);
		textViewLabelDestiny = (TextView) findViewById(R.id.textViewLabelDestiny);
		textViewDestiny = (TextView) findViewById(R.id.textViewDestiny);
		textViewLabelName = (TextView) findViewById(R.id.textViewLabelName);
		textViewName = (TextView) findViewById(R.id.textViewName);

		textViewLabelPsychic.setTextColor(Color.YELLOW);
		textViewPsychic.setTextColor(Color.WHITE);
		textViewLabelDestiny.setTextColor(Color.YELLOW);
		textViewDestiny.setTextColor(Color.WHITE);
		textViewLabelName.setTextColor(Color.YELLOW);
		textViewName.setTextColor(Color.WHITE);

		if (Numerology.isNumber) {
			textViewLabelPsychic.setText("Psychic Number: " + Numerology.psychic);
			textViewPsychic.setText(predictions[1][Numerology.psychic]);
			textViewLabelDestiny.setText("Destiny Number: " + Numerology.destiny);
			textViewDestiny.setText(predictions[2][Numerology.destiny]);

			textViewLabelName.setText("");
			textViewName.setText("");

		} else {
			textViewLabelName.setText("Name Number: " + Numerology.destiny);
			textViewName.setText(predictions[3][Numerology.destiny]);

			textViewLabelPsychic.setText("");
			textViewPsychic.setText("");
			textViewLabelDestiny.setText("");
			textViewDestiny.setText("");
		}

	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.predictions, menu);
		return true;
	}

}
