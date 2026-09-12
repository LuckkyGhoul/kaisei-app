package com.kaisei.discipline.data

import com.kaisei.discipline.data.model.KaiseiCharacter

/**
 * Modular character database. All dialogue below is ORIGINAL writing inspired
 * by each character's general personality/archetype — it is not copied from
 * any anime script. Swap `imageResName` for licensed/original art before any
 * public release; the app will render a text placeholder if the drawable is
 * missing, so nothing breaks.
 */
object CharacterPool {

    val characters: List<KaiseiCharacter> = listOf(
        KaiseiCharacter(
            characterId = "gojo",
            characterName = "Satoru Gojo",
            animeName = "Jujutsu Kaisen",
            archetype = "The Confident Mentor",
            imageResName = "char_gojo",
            japanesePhrase = "自分を信じろ",
            translation = "Believe in yourself.",
            appearanceWeight = 3,
            dialoguePool = listOf(
                "Relax. You didn't come this far to lose to a craving that doesn't even have a face.",
                "Strongest isn't a mood, it's a schedule you keep even when nobody's clapping.",
                "You looked temptation dead in the eye today and it blinked first. Not bad.",
                "Nobody's born disciplined. I just decided a long time ago I wasn't going to lose to myself."
            )
        ),
        KaiseiCharacter(
            characterId = "geto",
            characterName = "Suguru Geto",
            animeName = "Jujutsu Kaisen",
            archetype = "The Fallen Idealist",
            imageResName = "char_geto",
            japanesePhrase = "迷いは力を奪う",
            translation = "Doubt steals your strength.",
            appearanceWeight = 1,
            dialoguePool = listOf(
                "I once thought conviction alone was enough. It isn't — you need to renew it, every single day, like today.",
                "The version of you that gives up quietly is easier to become than you think. Don't let him win by default."
            )
        ),
        KaiseiCharacter(
            characterId = "sukuna",
            characterName = "Ryomen Sukuna",
            animeName = "Jujutsu Kaisen",
            archetype = "The Dominant King",
            imageResName = "char_sukuna",
            japanesePhrase = "弱さを許すな",
            translation = "Do not forgive weakness.",
            appearanceWeight = 2,
            dialoguePool = listOf(
                "A king doesn't negotiate with his own urges. He simply doesn't entertain them.",
                "You call it a craving. I call it a small, weak thing asking to be ruler of you. Refuse.",
                "Discipline is the only throne worth sitting on. You claimed it again today."
            )
        ),
        KaiseiCharacter(
            characterId = "toji",
            characterName = "Toji Fushiguro",
            animeName = "Jujutsu Kaisen",
            archetype = "The Ruthless Professional",
            imageResName = "char_toji",
            japanesePhrase = "感情を捨てろ",
            translation = "Cut the emotion out of it.",
            appearanceWeight = 1,
            dialoguePool = listOf(
                "Don't dress it up. You had a job today — hold the line — and you did it. Move on.",
                "Feelings are noise. The only thing that matters is whether you showed up. You did."
            )
        ),
        KaiseiCharacter(
            characterId = "dazai",
            characterName = "Osamu Dazai",
            animeName = "Bungo Stray Dogs",
            archetype = "The Calm Philosopher",
            imageResName = "char_dazai",
            japanesePhrase = "今日も生き延びた",
            translation = "You survived today too.",
            appearanceWeight = 3,
            dialoguePool = listOf(
                "Funny thing about urges — they promise the world and deliver a headache. You saw through it again.",
                "I've studied a hundred ways to give up. Somehow you keep choosing the one way to not.",
                "Boredom is just discipline's least dramatic enemy. You beat it anyway. How dull. How impressive.",
                "Even a man obsessed with endings has to admit — today you built, you didn't destroy."
            )
        ),
        KaiseiCharacter(
            characterId = "chuuya",
            characterName = "Chuuya Nakahara",
            animeName = "Bungo Stray Dogs",
            archetype = "The Fierce Perfectionist",
            imageResName = "char_chuuya",
            japanesePhrase = "妥協するな",
            translation = "Don't compromise.",
            appearanceWeight = 1,
            dialoguePool = listOf(
                "Short fuse, long memory — I don't forget who I promised to become, and neither should you.",
                "I don't do 'good enough.' Neither did you today. Keep that standard obnoxiously high."
            )
        ),
        KaiseiCharacter(
            characterId = "levi",
            characterName = "Levi Ackerman",
            animeName = "Attack on Titan",
            archetype = "The Disciplined Soldier",
            imageResName = "char_levi",
            japanesePhrase = "感情より結果",
            translation = "Results over feelings.",
            appearanceWeight = 3,
            dialoguePool = listOf(
                "Feelings pass. Standards don't. You kept the standard today.",
                "Weakness isn't the urge. Weakness is agreeing with it. You didn't.",
                "Clean up after yourself, every day, in every sense. Today, you did.",
                "No speeches. You held the line. That's the whole report."
            )
        ),
        KaiseiCharacter(
            characterId = "erwin",
            characterName = "Erwin Smith",
            animeName = "Attack on Titan",
            archetype = "The Resolute Commander",
            imageResName = "char_erwin",
            japanesePhrase = "心臓を捧げよ",
            translation = "Dedicate your heart.",
            appearanceWeight = 1,
            dialoguePool = listOf(
                "A commander gives his whole heart to the mission, not the parts that are convenient. You gave yours today.",
                "Sacrifice today's comfort for tomorrow's freedom. That trade is the only one worth making."
            )
        ),
        KaiseiCharacter(
            characterId = "eren",
            characterName = "Eren Yeager",
            animeName = "Attack on Titan",
            archetype = "The Relentless Will",
            imageResName = "char_eren",
            japanesePhrase = "自由を勝ち取れ",
            translation = "Fight for your freedom.",
            appearanceWeight = 2,
            dialoguePool = listOf(
                "Every craving is a small cage. Every day you refuse it, you tear down one more wall.",
                "I don't fight because it's easy. I fight because the alternative is being caged. So did you, today."
            )
        ),
        KaiseiCharacter(
            characterId = "itachi",
            characterName = "Itachi Uchiha",
            animeName = "Naruto",
            archetype = "The Quiet Sage",
            imageResName = "char_itachi",
            japanesePhrase = "己に打ち克つ",
            translation = "Conquer yourself.",
            appearanceWeight = 3,
            dialoguePool = listOf(
                "The strongest battles are invisible to everyone but you. You won one again today.",
                "A shinobi endures. Not loudly — quietly, day after day, until it simply becomes who he is.",
                "You sacrificed a small pleasure for a larger self. That trade is the whole of maturity.",
                "Real strength is choosing the harder, quieter path when no one is watching. You did."
            )
        ),
        KaiseiCharacter(
            characterId = "kakashi",
            characterName = "Kakashi Hatake",
            animeName = "Naruto",
            archetype = "The Steady Teacher",
            imageResName = "char_kakashi",
            japanesePhrase = "基本を忘れるな",
            translation = "Never forget the basics.",
            appearanceWeight = 2,
            dialoguePool = listOf(
                "Those who abandon their goals are trash. Those who abandon themselves for a moment's urge are worse. You didn't.",
                "Talent gets you started. Showing up, day after day, boring as it is — that's what actually gets you there."
            )
        ),
        KaiseiCharacter(
            characterId = "madara",
            characterName = "Madara Uchiha",
            animeName = "Naruto",
            archetype = "The Overwhelming Force",
            imageResName = "char_madara",
            japanesePhrase = "力は継続から生まれる",
            translation = "Power is born from persistence.",
            appearanceWeight = 1,
            dialoguePool = listOf(
                "Power isn't given. It's accumulated, one refused temptation at a time. You added to the pile today.",
                "The weak are ruled by impulse. The strong rule impulse. Simple. You chose your side today."
            )
        ),
        KaiseiCharacter(
            characterId = "sasuke",
            characterName = "Sasuke Uchiha",
            animeName = "Naruto",
            archetype = "The Obsessive Striver",
            imageResName = "char_sasuke",
            japanesePhrase = "目的を見失うな",
            translation = "Don't lose sight of your purpose.",
            appearanceWeight = 1,
            dialoguePool = listOf(
                "Obsession is dangerous unless it's aimed at the right target. Today, yours was aimed correctly.",
                "I've thrown everything away chasing the wrong things. Don't throw today away chasing the wrong thing either — you didn't."
            )
        ),
        KaiseiCharacter(
            characterId = "guts",
            characterName = "Guts",
            animeName = "Berserk",
            archetype = "The Endless Endurer",
            imageResName = "char_guts",
            japanesePhrase = "抗い続けろ",
            translation = "Keep struggling.",
            appearanceWeight = 3,
            dialoguePool = listOf(
                "Fate doesn't care what you want. You struggle anyway. Today, you struggled and won.",
                "A sword arm is just a habit repeated ten thousand times. So is self-control. Swing again tomorrow.",
                "The world doesn't owe you an easy day. You didn't ask for one. You just took it anyway.",
                "Scars don't stop a man from walking forward. Neither did today's urge. You kept walking."
            )
        ),
        KaiseiCharacter(
            characterId = "griffith",
            characterName = "Griffith",
            animeName = "Berserk",
            archetype = "The Calculated Ambition",
            imageResName = "char_griffith",
            japanesePhrase = "夢のために",
            translation = "For the sake of the dream.",
            appearanceWeight = 1,
            dialoguePool = listOf(
                "A dream demands everything, including the comfortable things you'd rather keep. You paid the toll today.",
                "Ambition without sacrifice is just a wish. You sacrificed something small today for something larger."
            )
        ),
        KaiseiCharacter(
            characterId = "thorfinn",
            characterName = "Thorfinn",
            animeName = "Vinland Saga",
            archetype = "The Reformed Warrior",
            imageResName = "char_thorfinn",
            japanesePhrase = "本当の強さとは",
            translation = "What true strength really is.",
            appearanceWeight = 3,
            dialoguePool = listOf(
                "I used to think strength meant never backing down. Now I know it means not letting anger — or urges — steer the ship.",
                "A true warrior has no enemies left except the ones inside. You beat one of yours today.",
                "Peace isn't the absence of a fight. It's winning the quiet fight, every day, the way you did today.",
                "I spent years running from myself. Standing still and saying no — that's the harder, better fight."
            )
        ),
        KaiseiCharacter(
            characterId = "askeladd",
            characterName = "Askeladd",
            animeName = "Vinland Saga",
            archetype = "The Cunning Survivor",
            imageResName = "char_askeladd",
            japanesePhrase = "頭を使え",
            translation = "Use your head.",
            appearanceWeight = 1,
            dialoguePool = listOf(
                "A clever man doesn't fight temptation head-on if he can simply out-think it. You out-thought it today.",
                "Survival is just discipline wearing a rougher coat. You survived the day the smart way."
            )
        ),
        KaiseiCharacter(
            characterId = "light",
            characterName = "Light Yagami",
            animeName = "Death Note",
            archetype = "The Calculating Idealist",
            imageResName = "char_light",
            japanesePhrase = "計画通り",
            translation = "Everything according to plan.",
            appearanceWeight = 1,
            dialoguePool = listOf(
                "Every great plan is built from small, boring, correct decisions. Today's was one of them.",
                "I plan for years ahead. You only had to plan for today, and you executed it perfectly."
            )
        ),
        KaiseiCharacter(
            characterId = "l",
            characterName = "L",
            animeName = "Death Note",
            archetype = "The Analytical Mind",
            imageResName = "char_l",
            japanesePhrase = "確率は変えられる",
            translation = "The odds can be changed.",
            appearanceWeight = 1,
            dialoguePool = listOf(
                "There's roughly a five percent chance you're actually incapable of this. The other ninety-five percent, you just proved.",
                "Interesting. The data today points to one conclusion: you are more disciplined than you give yourself credit for."
            )
        ),
        KaiseiCharacter(
            characterId = "lelouch",
            characterName = "Lelouch Lamperouge",
            animeName = "Code Geass",
            archetype = "The Strategic Commander",
            imageResName = "char_lelouch",
            japanesePhrase = "運命は変えられる",
            translation = "Fate can be changed.",
            appearanceWeight = 2,
            dialoguePool = listOf(
                "I command legions with a word. You commanded yourself today — a harder, rarer power.",
                "A plan is worthless without execution. Today you executed. Tomorrow, execute again."
            )
        ),
        KaiseiCharacter(
            characterId = "vegeta",
            characterName = "Vegeta",
            animeName = "Dragon Ball",
            archetype = "The Prideful Perfectionist",
            imageResName = "char_vegeta",
            japanesePhrase = "誇りを持て",
            translation = "Have pride in yourself.",
            appearanceWeight = 2,
            dialoguePool = listOf(
                "A prince does not grovel before a craving. Unacceptable. You refused it — acceptable, finally.",
                "I don't train to be 'good enough.' I train to be the best there's ever been. Train your restraint the same way."
            )
        ),
        KaiseiCharacter(
            characterId = "goku",
            characterName = "Goku",
            animeName = "Dragon Ball",
            archetype = "The Joyful Fighter",
            imageResName = "char_goku",
            japanesePhrase = "限界を超えろ",
            translation = "Go beyond your limit.",
            appearanceWeight = 2,
            dialoguePool = listOf(
                "Every time I hit a wall I got excited — a new limit to break! You broke one today.",
                "I don't fight to prove I'm strong. I fight because getting stronger feels good. So does this — doesn't it?"
            )
        ),
        KaiseiCharacter(
            characterId = "tanjiro",
            characterName = "Tanjiro Kamado",
            animeName = "Demon Slayer",
            archetype = "The Compassionate Fighter",
            imageResName = "char_tanjiro",
            japanesePhrase = "諦めない心",
            translation = "A heart that never gives up.",
            appearanceWeight = 3,
            dialoguePool = listOf(
                "Kindness to yourself sometimes means saying no to yourself. You were kind to your future self today.",
                "No matter how many times I fall, I get up with my breathing steady. You got up steady too.",
                "Set your heart ablaze — quietly, patiently. That's what you did today.",
                "I fight for the people I love, including the person I'll become. You fought for him today."
            )
        ),
        KaiseiCharacter(
            characterId = "rengoku",
            characterName = "Kyojuro Rengoku",
            animeName = "Demon Slayer",
            archetype = "The Blazing Mentor",
            imageResName = "char_rengoku",
            japanesePhrase = "心を燃やせ",
            translation = "Set your heart ablaze.",
            appearanceWeight = 1,
            dialoguePool = listOf(
                "Continue on! Whatever you believe in — continue to live and grow strong! You grew today!",
                "A flame that never wavers lights the path for others too. Keep yours burning steady."
            )
        ),
        KaiseiCharacter(
            characterId = "killua",
            characterName = "Killua Zoldyck",
            animeName = "Hunter x Hunter",
            archetype = "The Sharp Instinct",
            imageResName = "char_killua",
            japanesePhrase = "本能を鍛えろ",
            translation = "Sharpen your instincts.",
            appearanceWeight = 2,
            dialoguePool = listOf(
                "I was trained to feel nothing. I chose to feel things anyway — and to choose better ones. So did you.",
                "Instinct says take the easy path. Training says otherwise. Today, training won."
            )
        ),
        KaiseiCharacter(
            characterId = "hisoka",
            characterName = "Hisoka",
            animeName = "Hunter x Hunter",
            archetype = "The Patient Hunter",
            imageResName = "char_hisoka",
            japanesePhrase = "楽しみは我慢の後に",
            translation = "The reward comes after the patience.",
            appearanceWeight = 1,
            dialoguePool = listOf(
                "Mm~ delayed gratification is the only game worth playing. You're playing it well.",
                "The best things are worth waiting for, ripening slowly. You let today ripen instead of rushing it."
            )
        ),
        KaiseiCharacter(
            characterId = "kaneki",
            characterName = "Ken Kaneki",
            animeName = "Tokyo Ghoul",
            archetype = "The Reforged Self",
            imageResName = "char_kaneki",
            japanesePhrase = "自分を取り戻せ",
            translation = "Reclaim yourself.",
            appearanceWeight = 1,
            dialoguePool = listOf(
                "I was rebuilt piece by piece into someone I didn't choose. You're rebuilding yourself into someone you did. Keep going.",
                "It hurts to change. It hurts more to stay the same forever. You chose the harder, better pain today."
            )
        ),
        KaiseiCharacter(
            characterId = "ayanokoji",
            characterName = "Kiyotaka Ayanokoji",
            animeName = "Classroom of the Elite",
            archetype = "The Silent Strategist",
            imageResName = "char_ayanokoji",
            japanesePhrase = "感情を制御しろ",
            translation = "Control your emotions.",
            appearanceWeight = 1,
            dialoguePool = listOf(
                "Those who show their hand lose it. Those who master themselves need show nothing at all. You mastered something today.",
                "An impulse is just information. You read it, filed it, and moved on without acting on it. Efficient."
            )
        ),
        KaiseiCharacter(
            characterId = "jinwoo",
            characterName = "Sung Jin-Woo",
            animeName = "Solo Leveling",
            archetype = "The Relentless Ascender",
            imageResName = "char_jinwoo",
            japanesePhrase = "覚醒せよ",
            translation = "Awaken.",
            appearanceWeight = 3,
            dialoguePool = listOf(
                "I went from the weakest hunter to something else entirely, one grueling day at a time. This was one of yours.",
                "Level up isn't a cutscene. It's today, repeated, until you don't recognize the person you used to be.",
                "The dungeon doesn't care how you feel. Neither should your discipline. You showed up anyway.",
                "Arise. Not tomorrow — today. And today, you already did."
            )
        )
    )

    fun byId(id: String): KaiseiCharacter? = characters.firstOrNull { it.characterId == id }
}
