package br.com.animator.game;

import br.com.animator.audio.AudioManager;
import br.com.animator.util.ImageManager;

public class LoadResources {

    public static void loadAllImages() {
        // Assets para GameScorePresentationImpl
        ImageManager.load("DeveloperAdvertise.logo", "/images/logo_phoenix.png");


        ImageManager.load("GameIntro.1", "/images/transparence_intro_top.png");
        ImageManager.load("GameIntro.2", "/images/transparence_intro_bottom.png");
        ImageManager.load("GameIntro.3", "/images/text_intro_1.png");
        ImageManager.load("GameIntro.4", "/images/text_intro_2.png");

        ImageManager.load("GameExitMenu.1","/images/quit_bg.png");
		ImageManager.load("GameExitMenu.2","/images/really_quit_question.png");
		ImageManager.load("GameExitMenu.3","/images/yes_button.png");
		ImageManager.load("GameExitMenu.4","/images/no_button.png");
		ImageManager.load("GameExitMenu.5","/images/yes_no_highlight.png");
        
        ImageManager.load("LogoIntro.1", "/images/logo_p1.png");
		ImageManager.load("LogoIntro.2", "/images/logo_p2.png");
		ImageManager.load("LogoIntro.3", "/images/logo_p3.png");
		ImageManager.load("LogoIntro.4", "/images/logo_p4.png");
		ImageManager.load("LogoIntro.5", "/images/logo_p5.png");
		ImageManager.load("LogoIntro.6", "/images/bt_press.png");

        ImageManager.load("Loading.1", "/images/loading_1a.png");
		ImageManager.load("Loading.2", "/images/loading_2a.png");
		ImageManager.load("Loading.3", "/images/loading_3a.png");
		ImageManager.load("Loading.4", "/images/loading_4a.png");

        ImageManager.load("MainMenu.1", "/images/logo_p1.png");
		ImageManager.load("MainMenu.2", "/images/logo_p2.png");
		ImageManager.load("MainMenu.3", "/images/logo_p3.png");
		ImageManager.load("MainMenu.4", "/images/logo_p4.png");
		ImageManager.load("MainMenu.5", "/images/logo_p5.png");
		ImageManager.load("MainMenu.6", "/images/start_game_button.png");
		ImageManager.load("MainMenu.7", "/images/options_button.png");
		ImageManager.load("MainMenu.8", "/images/quit_game_button.png");
		ImageManager.load("MainMenu.9", "/images/mainmenu_selection.png");

        ImageManager.load("GameMainOption.1", "/images/bg_option_screen.png");
		ImageManager.load("GameMainOption.2", "/images/opt_game_option_button.png");
		ImageManager.load("GameMainOption.3", "/images/opt_configure_keys_button.png");
		ImageManager.load("GameMainOption.4", "/images/opt_gfx_settings_button.png");
		ImageManager.load("GameMainOption.5", "/images/opt_sfx_settings_button.png");
		ImageManager.load("GameMainOption.6", "/images/opt_back_mainmenu_button.png");
		ImageManager.load("GameMainOption.7", "/images/opt_mainoption_selector.png");

        ImageManager.load("GameScore.1", "/images/hall.png");
		ImageManager.load("GameScore.2", "/images/numbers.png");
		ImageManager.load("GameScore.3", "/images/letter_lower.png");
		ImageManager.load("GameScore.4", "/images/letter_upper.png");

        ImageManager.load("GameGraphicsConfig.1", "/images/bg_option_screen5.png");
		ImageManager.load("GameGraphicsConfig.2", "/images/logo_graphics_config.png");
		ImageManager.load("GameGraphicsConfig.3", "/images/optg_gameoption_selector.png");
		ImageManager.load("GameGraphicsConfig.4", "/images/gfx_screen_res_label.png");
		ImageManager.load("GameGraphicsConfig.5", "/images/gfx_enable_triple_label.png");
		ImageManager.load("GameGraphicsConfig.6", "/images/gfx_screen_mode_label.png");
		ImageManager.load("GameGraphicsConfig.7", "/images/gfx_deep_color_label.png");
		ImageManager.load("GameGraphicsConfig.8", "/images/gfx_apply_label.png");
		ImageManager.load("GameGraphicsConfig.9", "/images/gfx_cancel_label.png");
		ImageManager.load("GameGraphicsConfig.10", "/images/gfx_number_value.png");
		ImageManager.load("GameGraphicsConfig.11", "/images/gfx_screen_mode_value.png");
		ImageManager.load("GameGraphicsConfig.12", "/images/gfx_deep_colors_value.png");
		ImageManager.load("GameGraphicsConfig.13", "/images/optg_check.png");
		ImageManager.load("GameGraphicsConfig.14", "/images/optg_uncheck.png");
		ImageManager.load("GameGraphicsConfig.15", "/images/optg_yes.png");
		ImageManager.load("GameGraphicsConfig.16", "/images/optg_no.png");
		ImageManager.load("GameGraphicsConfig.17", "/images/bt_arrow_left.png");
		ImageManager.load("GameGraphicsConfig.18", "/images/bt_arrow_right.png");
		ImageManager.load("GameGraphicsConfig.19", "/images/bt_arrow_left_white.png");
		ImageManager.load("GameGraphicsConfig.20", "/images/bt_arrow_right_white.png");
		ImageManager.load("GameGraphicsConfig.21", "/images/gfx_must_apply.png");

        ImageManager.load("GameOptionsConfig.1", "/images/bg_option_screen2.png");
		ImageManager.load("GameOptionsConfig.2", "/images/logo_game_options.png");
		ImageManager.load("GameOptionsConfig.3", "/images/optg_gameoption_selector.png");
		ImageManager.load("GameOptionsConfig.4", "/images/optg_game_difficulty_label.png");
		ImageManager.load("GameOptionsConfig.5", "/images/optg_rest_label.png");
		ImageManager.load("GameOptionsConfig.6", "/images/optg_extra_life_label.png");
		ImageManager.load("GameOptionsConfig.7", "/images/optg_continues_label.png");
		ImageManager.load("GameOptionsConfig.8", "/images/optg_game_subtitles_label.png");
		ImageManager.load("GameOptionsConfig.9", "/images/optg_back.png");
		ImageManager.load("GameOptionsConfig.10", "/images/bt_arrow_left.png");
		ImageManager.load("GameOptionsConfig.11", "/images/bt_arrow_right.png");
		ImageManager.load("GameOptionsConfig.12", "/images/bt_arrow_left_white.png");
		ImageManager.load("GameOptionsConfig.13", "/images/bt_arrow_right_white.png");
		ImageManager.load("GameOptionsConfig.14", "/images/optg_check.png");
		ImageManager.load("GameOptionsConfig.15", "/images/optg_uncheck.png");
		ImageManager.load("GameOptionsConfig.16", "/images/optg_yes.png");
		ImageManager.load("GameOptionsConfig.17", "/images/optg_no.png");
		ImageManager.load("GameOptionsConfig.18", "/images/optg_easy_normal_hard_label.png");
		ImageManager.load("GameOptionsConfig.19", "/images/optg_rest_values_label.png");
		ImageManager.load("GameOptionsConfig.20", "/images/optg_continue_values_label.png");
		ImageManager.load("GameOptionsConfig.21", "/images/optg_extra_life_values_label.png");

        ImageManager.load("GameSoundConfig.1", "/images/bg_option_screen4.png");
		ImageManager.load("GameSoundConfig.2", "/images/logo_sound_config.png");
		ImageManager.load("GameSoundConfig.3", "/images/optg_gameoption_selector.png");
		ImageManager.load("GameSoundConfig.4", "/images/sound_enable_music_label.png");
		ImageManager.load("GameSoundConfig.5", "/images/sound_enable_sfx_label.png");
		ImageManager.load("GameSoundConfig.6", "/images/sound_music_volume_label.png");
		ImageManager.load("GameSoundConfig.7", "/images/sound_sfx_volume_label.png");
		ImageManager.load("GameSoundConfig.8", "/images/optg_back.png");
		ImageManager.load("GameSoundConfig.9", "/images/optg_check.png");
		ImageManager.load("GameSoundConfig.10", "/images/optg_uncheck.png");
		ImageManager.load("GameSoundConfig.11", "/images/optg_yes.png");
		ImageManager.load("GameSoundConfig.12", "/images/optg_no.png");
		ImageManager.load("GameSoundConfig.13", "/images/volume_0.png");
		ImageManager.load("GameSoundConfig.14", "/images/volume_1.png");
		ImageManager.load("GameSoundConfig.15", "/images/volume_2.png");
		ImageManager.load("GameSoundConfig.16", "/images/volume_3.png");
		ImageManager.load("GameSoundConfig.17", "/images/volume_4.png");
		ImageManager.load("GameSoundConfig.18", "/images/volume_5.png");
		ImageManager.load("GameSoundConfig.19", "/images/volume_6.png");
		ImageManager.load("GameSoundConfig.20", "/images/volume_7.png");
		ImageManager.load("GameSoundConfig.21", "/images/volume_8.png");
		ImageManager.load("GameSoundConfig.22", "/images/volume_9.png");

 

    }

    public static void loadAllSFX() {
        AudioManager.loadSFX("menu.change", "/audio/menu1.ogg");
    }

    public static void loadAllMusics() {
        AudioManager.loadMusic("menu.music", "/audio/menumusic.ogg");
    }
}