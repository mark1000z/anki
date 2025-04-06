package com.example.app.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.app.domain.Anki;
import com.example.app.mapper.AnkiMapper;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AnkiController {

    private final AnkiMapper mapper; // Mapperのインジェクション


    
    @PostMapping("/ankiForm")
    public String saveWord(@RequestParam String word, @RequestParam String meaning,
                           @RequestParam(required = false) String learned, Model model) {
        boolean isLearned = (learned != null && learned.equals("true"));
        
        try {
            mapper.addWord(word, meaning, isLearned);
            model.addAttribute("message", "単語が保存されました");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "エラーが発生しました: " + e.getMessage());
        }

        return "redirect:/ankiForm";
    }

    // ページ分割された単語一覧を表示
    @GetMapping("/ankiForm")
    public String ankiForm(@RequestParam(defaultValue = "1") int page, Model model) {
        int pageSize = 10; // 1ページあたりの単語数
        int offset = (page - 1) * pageSize;

        // すべての単語リストをページネーション付きで取得
        List<Anki> allWords = mapper.getAllWordsPaginated(pageSize, offset); // 新しいメソッドに変更
        int totalWords = mapper.countAllWords(); // 全体のカウント用メソッド
        int totalPages = (int) Math.ceil((double) totalWords / pageSize);

        // モデルに値を追加
        model.addAttribute("words", allWords);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "ankiForm";
    }



    @GetMapping("/ankiForm/unlearned")
    public String unlearnedWords(@RequestParam(defaultValue="1")int page,Model model) {
    	int pageSize=10;
    	int offset=(page -1)* pageSize;
    	
    	List<Anki>unlearnedWords=mapper.getUnlearnedWordsPaginated(pageSize,offset);
    	int totalUnlearnedWords=mapper.countUnlearnedWords ();
    	int totalPages=(int)Math.ceil((double)totalUnlearnedWords / pageSize);
    	
    	model.addAttribute("words",unlearnedWords);
    	model.addAttribute("currentPage",page);
    	model.addAttribute("totalPages",totalPages);
    	
    	return "ankiForm";
    	
    }
    
    @PostMapping("/checkAnswer")
    public String checkAnswer(@RequestParam("answer") String answer,
                              @RequestParam("question") String question,
                              @RequestParam("correctAnswer") String correctAnswer,
                              @RequestParam("difficulty") int difficulty,
                              Model model) {
        // 解答が正しいかどうかを判定
        boolean isCorrect = answer.equals(correctAnswer);

        // 正解/不正解のメッセージを設定
        String resultMessage = isCorrect ? "正解！" : "不正解...";

        // 単語のlearnedステータスを更新する処理
        try {
        	 int learned = isCorrect ? 1 : 0; // 正解なら1（覚えた）、不正解なら0（未習得）
            mapper.updateWordStatus(question, learned); // ここでwordのlearnedステータスを更新
            model.addAttribute("message", "単語のステータスが更新されました");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "更新中にエラーが発生しました: " + e.getMessage());
            return "error";
        }

        // モデルに結果メッセージを追加
        model.addAttribute("resultMessage", resultMessage);
        model.addAttribute("difficulty", difficulty); // 難易度も追加

        // 次の問題に進むために結果ページに遷移
        return "result"; // 例: /resultに遷移
    }
    
    

    @PostMapping("/checkAnswer2")
    public String checkAnswer2(@RequestParam("answer") String answer,
                               @RequestParam("question") String question,
                               @RequestParam("correctAnswer") String correctAnswer,
                               @RequestParam("difficulty") int difficulty,
                               Model model) {
        // 解答が正しいかどうかを判定
        boolean isCorrect = answer.equals(correctAnswer);

        // 正解/不正解のメッセージを設定
        String resultMessage = isCorrect ? "正解！" : "不正解...";

        // learnedWords のステータスを更新
        try {
            // 正解なら1（覚えた）、不正解なら0（未習得）、忘れた場合は2（忘れた）
            int learned = (isCorrect ? 1 : 0);
            if (!isCorrect) { // もし不正解だった場合は、忘れたとして2に設定
                learned = 2;
            }
            mapper.updateWordStatus(question, learned); // ステータス更新
            model.addAttribute("message", "覚えた単語のステータスが更新されました");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "更新中にエラーが発生しました: " + e.getMessage());
            return "error";
        }

        // 結果メッセージの設定
        model.addAttribute("resultMessage", resultMessage);
        model.addAttribute("difficulty", difficulty); // 難易度も追加

        // 結果ページに遷移
        return "result2"; // result2.htmlに遷移
    }



    
    
    
    
    
    
	

    @PostMapping("/delete")
    public String deleteWord(@RequestParam("word") String word, Model model) {
        try {
            mapper.deleteWordByWord(word); // 静的メソッド呼び出しを削除
            model.addAttribute("message", "単語が削除されました");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "削除中にエラーが発生しました: " + e.getMessage());
            return "error";
        }
        return "redirect:/ankiForm";
    }

    @PostMapping("/forgetWord")
    public String forgetWord(@RequestParam("word") String wordToForget, Model model) {
        try {
            mapper.markWordAsUnlearned(wordToForget); // 静的メソッド呼び出しを削除
            model.addAttribute("message", "単語が「未習得」に戻されました");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "データベース接続中にエラーが発生しました: " + e.getMessage());
            return "error";
        }
        return "redirect:/learnedWords";
    }

	 @PostMapping("/learned")
	public String markAsLearned(@RequestParam("word") String word, Model model) {
	    try {
	        // 単語を「習得済み」にマーク
	        mapper.markWordAsLearned(word);
	
	        // 更新後、習得済み単語を除外した未習得単語リストを取得
	        List<Anki> unlearnedWords = mapper.getUnlearnedWords(); // 未習得単語のリストを取得
	
	        // 更新後の未習得単語リストをモデルに追加
	        model.addAttribute("words", unlearnedWords);
	
	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("errorMessage", "単語の更新中にエラーが発生しました: " + e.getMessage());
	        return "error";
	    }
	
	    // 更新後、/learnedページにリダイレクト
	    return "redirect:/learned";
	}
	
   
    
	 @GetMapping("/learned")
	 public String getUnlearnedWords(Model model) {
	     try {
	         // 未習得または忘れた単語を取得
	         List<Anki> learnedWords = mapper.getUnlearnedWords();  // 修正されたクエリを使用
	         model.addAttribute("learnedWords", learnedWords);
	     } catch (Exception e) {
	         model.addAttribute("errorMessage", "データベース接続中にエラーが発生しました: " + e.getMessage());
	         return "error"; // エラーページにリダイレクト
	     }
	     return "learned"; // learned.htmlを返す
	 }


    
    

    @GetMapping("/learnedWords")
    public String getLearnedWords(Model model) {
        try {
            List<Map<String, String>> learnedWords = mapper.getLearnedWords(); // 覚えた単語を取得
            model.addAttribute("learnedWords", learnedWords);

            // クイズの問題を作成
            List<Map<String, Object>> quizData = new ArrayList<>();

            for (Map<String, String> word : learnedWords) {
                String correctAnswer = word.get("meaning");

                // ダミーの選択肢を取得（他の単語の意味をランダムに取得）
                List<String> options = mapper.getRandomMeaningsExcept(correctAnswer, 3); // 他の意味をランダムに取得

                // 正解を選択肢に追加し、シャッフル
                options.add(correctAnswer);
                Collections.shuffle(options); // ランダムに並べ替え

                // 問題データを作成
                Map<String, Object> quizItem = new HashMap<>();
                quizItem.put("question", word.get("word")); // 単語
                quizItem.put("answers", options); // シャッフルされた選択肢
                quizItem.put("correctAnswer", correctAnswer); // 正解
                quizItem.put("wordId", word.get("id")); // 単語ID（必要に応じて）

                quizData.add(quizItem);
            }

            model.addAttribute("quizData", quizData); // クイズデータをモデルに追加
        } catch (Exception e) {
            model.addAttribute("errorMessage", "データベース接続中にエラーが発生しました: " + e.getMessage());
            return "error";
        }

        return "learnedWordsQuiz"; // クイズページに遷移
    }






    @GetMapping("/quiz")
    public String getQuiz(Model model, @RequestParam(value = "difficulty", defaultValue = "2") int difficulty) {
        try {
            List<Anki> words = mapper.getUnlearnedWords();

            if (words.size() >= difficulty) {
                Collections.shuffle(words);

                // 正解と不正解の答えを決定
                Anki correctAnswer = words.get(0);

                // 不正解の答えを追加でランダムに取得
                List<String> wrongAnswers = new ArrayList<>();
                for (int i = 1; i < difficulty; i++) {
                    wrongAnswers.add(words.get(i).getMeaning());
                }

                // 正解も不正解リストに追加
                List<String> allAnswers = new ArrayList<>(wrongAnswers);
                allAnswers.add(correctAnswer.getMeaning());
                
                // シャッフルしてランダムな順番に
                Collections.shuffle(allAnswers);

                // モデルに質問、選択肢を追加
                model.addAttribute("question", correctAnswer.getWord());
                model.addAttribute("answers", allAnswers);
                model.addAttribute("correctAnswer", correctAnswer.getMeaning());
                model.addAttribute("difficulty", difficulty); // 難易度を渡す

                return "quiz";
            } else {
                model.addAttribute("errorMessage", "十分な単語がありません");
                return "error";
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "エラーが発生しました: " + e.getMessage());
            return "error";
        }
    }
    @PostMapping("/answer")
    public String processAnswer(@RequestParam String word, @RequestParam boolean isCorrect, Model model) {
        try {
            int learned = isCorrect ? 1 : 2; // 正解なら1（覚えた）、不正解なら2（忘れた）
            mapper.updateWordStatus(word, learned);
            model.addAttribute("message", "単語の状態が更新されました");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "更新中にエラーが発生しました: " + e.getMessage());
        }
        return "redirect:/quiz"; // クイズページにリダイレクト
    }
    
    @PostMapping("/updateStatus")
    @Transactional
    public String updateLearnedStatus(@RequestParam Long id, @RequestParam int learned) {
        try {
            System.out.println("ID: " + id + ", Learned: " + learned); // デバッグ用
            mapper.updateLearnedStatus(id, learned);
        } catch (Exception e) {
            e.printStackTrace(); // 例外が発生している場合に出力
        }
        return "redirect:/ankiForm"; // 更新後、戻るページにリダイレクト
    }


}



