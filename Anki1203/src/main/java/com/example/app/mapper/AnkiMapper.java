package com.example.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.app.domain.Anki;

@Mapper
public interface AnkiMapper {

    // 単語を追加
    void addWord(String word, String meaning, boolean learned);

    // 単語を挿入（同じ単語が既にある場合エラー）
    void insertWord(String word, String meaning, boolean learned);

    // 指定された単語を削除
    void deleteWordByWord(String word);

    // 指定された単語を「未習得」に変更
    void markWordAsUnlearned(String word);

    // 指定された単語を「習得済み」に変更
    void markWordAsLearned(String word);

    // 未習得の単語リストを取得
    List<Anki> getUnlearnedWords();
    
    
    
    List<Anki>getlearnedWords();
    
    

    // 学習済みの単語リストを取得
    List<Map<String, String>> getLearnedWords();
    
    
    
    
    List<Anki> getWordsPaginated(@Param("limit") int limit, @Param("offset") int offset);
    int countWords();

	List<Anki> getUnlearnedWordsPaginated(@Param("limit")int limit, @Param("offset")int offset);

	int countUnlearnedWords();

	

	

	void updateLearnedStatus(@Param("id") Long id, @Param("learned") int learned);
		// TODO 自動生成されたメソッド・スタブ

		
	void updateWordStatus(@Param("word") String word, @Param("learned") int learned);

	

	 List<String> getRandomMeaningsExcept(@Param("correctAnswer") String correctAnswer, @Param("limit") int limit);

	//List<Anki> getAllWordsPaginated(int pageSize, int offset);

	//int countAllWords();
	

	List<Anki> getAllWordsPaginated(@Param("limit") int limit, @Param("offset") int offset); // 新しいメソッド
    int countAllWords(); // すべての単語数を取得するメソッド

	
	
	
	
}



