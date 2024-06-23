package com.growgenie.kidsyCore.model.screenState.screen.plan

/*
TODO: Make Plan content easier to access. It can not be just one line of json
TODO: Map Data collected via Plan JSON to userSessionData for easier retrival
TODO: Prompt that generate json changes ids of screen every time it runs, the prompt has to be fixed to stay consistent
*/
class Plans {
    companion object {
        const val onboarding: String =
            """
                {
                "screens": [
                    {
                        "type": "Prompt",
                        "id": "startPersonalization",
                        "headline": "Answer a few questions to start personalizing your experience.",
                        "submitTop": "Tap anywhere to continue."
                    },
                    {
                        "type": "QuestionMultiselect",
                        "id": "parentingStruggles",
                        "headline": "Select your biggest parenting struggles.",
                        "options": [
                            { "id": 1, "text": "Sleep" },
                            { "id": 2, "text": "Health" },
                            { "id": 3, "text": "Eating" },
                            { "id": 4, "text": "Behaviour" }
                        ],
                        "submit": "Continue"
                    },
                    {
                        "type": "Question",
                        "id": "topPriority",
                        "headline": "Select your top priority",
                        "options": [
                            { "id": 1, "text": "Sleep" },
                            { "id": 2, "text": "Health" }
                        ],
                        "submit": "Continue"
                    },
                    {
                        "type": "Question",
                        "id": "kidAge",
                        "headline": "What best describes your kid’s age?",
                        "options": [
                            { "id": 1, "text": "Newborn (0-3mo)" },
                            { "id": 2, "text": "Baby (3-12mo)" },
                            { "id": 3, "text": "Toddler (1-3 year)" },
                            { "id": 4, "text": "Preschool (3-5 year)" },
                            { "id": 5, "text": "School (5-11 year)" },
                            { "id": 6, "text": "Teen (11+ year)" }
                        ],
                        "submit": "Continue"
                    },
                    {
                        "type": "TextInput",
                        "id": "childNickname",
                        "headline": "How do you call your child?",
                        "textInputPlaceholder": "Name",
                        "submit": "Continue"
                    },
                    {
                        "type": "PromptWithDescriptionPoints",
                        "id": "memberFeedback",
                        "headline": "Here’s what our members are saying:",
                        "options": [
                            { "id": 1, "text": "87% Improved toddler sleep", "imageName": "moon_blue" },
                            { "id": 2, "text": "82% Reduced newborn sick time", "imageName": "apple_purple" }
                        ],
                        "submitTop": "Based on a study of members who use Kidsy 5 times per week.",
                        "submit": "Continue"
                    },
                    {
                        "type": "QuestionMultiselect",
                        "id": "sleepProblems",
                        "headline": "What sleep problems are you facing?",
                        "options": [
                            { "id": 1, "text": "Frequent wake-ups" },
                            { "id": 2, "text": "Time to fall asleep" },
                            { "id": 3, "text": "Early wakeups" },
                            { "id": 4, "text": "Chaotic nap schedule" }
                        ],
                        "submit": "Continue"
                    },
                    {
                        "type": "Question",
                        "id": "fallAsleepDuration",
                        "headline": "How long does it usually take to fall asleep?",
                        "options": [
                            { "id": 1, "text": "5-15 minutes" },
                            { "id": 2, "text": "15-30 minutes" },
                            { "id": 3, "text": "30+ minutes" }
                        ]
                    },
                    {
                        "type": "Question",
                        "id": "toddlerNaps",
                        "headline": "How many naps does your toddler have?",
                        "options": [
                            { "id": 1, "text": "1" },
                            { "id": 2, "text": "2" },
                            { "id": 3, "text": "None" }
                        ]
                    },
                    {
                        "type": "Question",
                        "id": "parentRole",
                        "headline": "What best describes your role?",
                        "options": [
                            { "id": 1, "text": "Mom" },
                            { "id": 2, "text": "Dad" },
                            { "id": 3, "text": "Caregiver" }
                        ]
                    },
                    {
                        "type": "Question",
                        "id": "singleParent",
                        "headline": "Are you a single parent?",
                        "options": [
                            { "id": 1, "text": "Yes" },
                            { "id": 2, "text": "No" }
                        ]
                    },
                    {
                        "type": "Prompt",
                        "id": "kidsyImpact",
                        "headlineTop": "Did you know?",
                        "headline": "Kidsy helped 320,000 single moms improve their kids' sleep.",
                        "headlineBottom": "As of July 2024",
                        "submit": "Continue"
                    },
                    {
                        "type": "Question",
                        "id": "learnTime",
                        "headline": "When would you like to learn new parenting techniques?",
                        "options": [
                            { "id": 1, "text": "Morning" },
                            { "id": 2, "text": "Afternoon" },
                            { "id": 3, "text": "Evening" }
                        ]
                    },
                    {
                        "type": "AskForNotification",
                        "id": "dailyReminder",
                        "headline": "Get a daily reminder to meet your goals",
                        "headlineBottom": "Reminders help you build better habits.",
                        "headlineBottomImageName": "notification_cta",
                        "submit": "Continue"
                    },
                    {
                        "type": "TimeInput",
                        "id": "reminderTime",
                        "headline": "When would you like to be reminded?",
                        "headlineBottom": "Select Time",
                        "submit": "Set reminder"
                    }
                ]
            }
            """
        const val day0: String =
            """
            {
                "id": "newborns_sleep_day1",
                "navigationBarTitle": "Newborn’s Sleep",
                "headline": "Day 1",
                "headlineBottom": "Understanding Newborn Sleep",
                "submit": "Begin",
                "screens": [
                    {
                        "type": "Prompt",
                        "id": "welcome_screen",
                        "headline": "Welcome to {childNickname}’s Sleep program, {user_name}!",
                        "submitTop": "Tap anywhere to continue."
                    },
                    {
                        "type": "Question",
                        "id": "track_sleep_question",
                        "headline": "Do you track {childNickname} sleep?",
                        "options": [
                            { "id": 1, "text": "Yes" },
                            { "id": 2, "text": "No" },
                            { "id": 3, "text": "Sometimes" },
                            { "id": 4, "text": "Don't know" }
                        ],
                        "submit": "Continue"
                    },
                    {
                        "type": "Prompt",
                        "id": "tracking_sleep_info",
                        "headline": "Tracking sleep patterns can help improve your baby's sleep quality.",
                        "submitTop": "Tap anywhere to continue."
                    },
                    {
                        "type": "Prompt",
                        "id": "tracking_naps_info",
                        "headline": "Tracking naps ensures your baby gets enough rest throughout the day.",
                        "submitTop": "Tap anywhere to continue."
                    },
                    {
                        "type": "Prompt",
                        "id": "rested_babies_info",
                        "headline": "It also makes your life easier. Well rested babies tend to be less nervous.",
                        "submitTop": "Tap anywhere to continue."
                    },
                    {
                        "type": "Question",
                        "id": "average_sleep_question",
                        "headline": "On average, how much total sleep does {childNickname} get in a day?",
                        "options": [
                            { "id": 1, "text": "12-16" },
                            { "id": 2, "text": "16-20" },
                            { "id": 3, "text": "20+" },
                            { "id": 4, "text": "Don't know" }
                        ]
                    },
                    {
                        "type": "Prompt",
                        "id": "newborn_sleep_info",
                        "headline": "This is great! Newborns need 16-20 hours of sleep per day.",
                        "submitTop": "Tap anywhere to continue."
                    },
                    {
                        "type": "Prompt",
                        "id": "sleep_aids_info",
                        "headline": "Sleep aids memory, senses, and exploration readiness.",
                        "submitTop": "Tap anywhere to continue."
                    },
                    {
                        "type": "Prompt",
                        "id": "track_nina_sleep",
                        "headline": "For the next 5 days track Nina sleep as much as precise as you can.",
                        "submitTop": "Tap anywhere to continue."
                    },
                    {
                        "type": "Prompt",
                        "id": "uncover_routine_info",
                        "headline": "It will uncover {childNickname} routine and help identify best patterns.",
                        "submitTop": "Tap anywhere to continue."
                    }
                ]
            }
            """
    }
}